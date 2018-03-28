package com.yjl.distributed.mq.config.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;

/**
 * 
 * @author zhaoyc
 * 
 */
public final class FileUtils {
    private static AtomicInteger invokingCount = new AtomicInteger(0);
    private static AtomicInteger commonLogCount = new AtomicInteger(0);
    private static int invokingFlushCount = 1; // 达到多少记录数就持久化
    private static int commonLogFlushCount = 1; // 达到多少记录数就持久化
    private static FileWriter invokingLog = null; // 接口调用日志文件，各文件不能混用
    private static FileWriter commonLog = null; // 通用业务日志文件，各文件不能混用
    private static long invokingTime = System.currentTimeMillis();
    private static long commonLogTime = System.currentTimeMillis();
    private static FastDateFormat df =
            FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", null, null);

    private static String invokingLogFile = ""; // 接口调用日志
    private static String commonLogFile = ""; // 通用业务日志文件

    static {
        // 心跳监控数据是否需要写磁盘
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!StringUtils.isEmpty(invokingLogFile)) {
                            writeInvokingLog(invokingLogFile, "", invokingFlushCount);
                        }
                        if (!StringUtils.isEmpty(commonLogFile)) {
                            writeCommonLog(commonLogFile, "", commonLogFlushCount);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 写接口调用日志文件，各文件不能混用
     * 
     * @param file
     * @param str
     * @param flushCount 达到多少记录数就持久化
     * @throws IOException
     */
    public static void writeInvokingLog(String file, String str, int flushCount)
            throws IOException {
        new File(file.substring(0, file.lastIndexOf("/"))).mkdirs();
        invokingLogFile = file;
        invokingFlushCount = flushCount;
        StringBuilder data = new StringBuilder();
        data.append(df.format(System.currentTimeMillis())).append(" ");// 时间前缀
        data.append(str).append("\r\n");

        if (invokingLog == null) {
            invokingLog = new FileWriter(file, true);
        }

        // 空字符串为心跳数据，不写文件
        if (!StringUtils.isEmpty(str)) {
            invokingLog.write(data.toString());// 写文件丢到单独线程或者服务器，批量写
        }

        if (invokingCount.incrementAndGet() >= flushCount
                || (System.currentTimeMillis() - invokingTime) > 60000) {
            invokingLog.flush();// 100条记录或者超过一分钟提交写一次磁盘
            invokingCount.set(0);// 重新计算
            invokingTime = System.currentTimeMillis();

            // 单个文件大于10M就切换新文件
            File f = new File(invokingLogFile);
            if (f.length() > 10485760) {
                invokingLog.close();
                invokingLog = null;
                f.renameTo(new File(invokingLogFile + "." + System.currentTimeMillis()));
            }

        }

    }



    /**
     * 写通用业务日志文件，各文件不能混用
     * 
     * @param file
     * @param str
     * @param flushCount 达到多少记录数就持久化
     * @throws IOException
     */
    public static void writeCommonLog(String file, String str, int flushCount) throws IOException {
        new File(file.substring(0, file.lastIndexOf("/"))).mkdirs();
        commonLogFile = file;
        commonLogFlushCount = flushCount;
        StringBuilder data = new StringBuilder();
        data.append(df.format(System.currentTimeMillis())).append(" ");// 时间前缀
        data.append(str).append("\r\n");

        if (commonLog == null) {
            commonLog = new FileWriter(file, true);
        }

        // 空字符串为心跳数据，不写文件
        if (!StringUtils.isEmpty(str)) {
            commonLog.write(data.toString());// 写文件丢到单独线程或者服务器，批量写
        }

        if (commonLogCount.incrementAndGet() >= flushCount
                || (System.currentTimeMillis() - commonLogTime) > 60000) {
            commonLog.flush();// 100条记录或者超过一分钟提交写一次磁盘
            commonLogCount.set(0);// 重新计算
            commonLogTime = System.currentTimeMillis();


            // 单个文件大于10M就切换新文件
            File f = new File(commonLogFile);
            if (f.length() > 10485760) {
                commonLog.close();
                commonLog = null;
                f.renameTo(new File(commonLogFile + "." + System.currentTimeMillis()));
            }

        }
    }

    /**
     * 清除序列化对象文件
     * 
     * @param path 文件/目录路径
     * @param delThreads 删除文件的线程池大小
     */
    public static void deleteFiles(String path, int delThreads) {
        LogManager.getLogger().info("delete files ... path=" + path);

        File dir = new File(path);

        // 单个文件删除
        if (dir.isFile()) {
            dir.delete();
            return;
        }

        File[] fs = dir.listFiles();
        if (fs == null) {
            return;
        }

        for (final File file : fs) {
            ThreadPoolUtils.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    delete(file);
                }

                /**
                 * 递归删除指定路径下的所有文件
                 * 
                 * @param file 文件/目录路径
                 */
                private void delete(File file) {
                    if (file == null) {
                        return;
                    }

                    if (file.isFile()) {
                        file.delete();// 单个文件删除
                    } else { // 文件夹
                        String[] list = file.list();// 文件列表名称
                        if (list == null || list.length == 0) {
                            file.delete();// 空文件夹删除
                        } else {
                            File[] fs = file.listFiles();
                            if (fs != null) {
                                for (File f : fs) {
                                    delete(f);// 递归删除每一个文件
                                    f.delete();// 删除该文件夹
                                }
                            }
                        }
                    }
                    file.delete();
                }

            });
        }

    }


    /**
     * 获取图片文件后缀，包括.号，如果没有则返回.jpg
     * 
     * @param url
     * @return
     */
    public static String getPicExpiration(String url) {
        String expiration = url.substring(url.lastIndexOf("."));
        if (expiration == null || "".equals(expiration)) {
            return ".jpg";
        } else if (".jpg".equals(expiration)) {
            return ".jpg";
        } else if (".jpeg".equals(expiration)) {
            return ".jpeg";
        } else if (".png".equals(expiration)) {
            return ".png";
        } else if (".gif".equals(expiration)) {
            return ".gif";
        } else if (".bmp".equals(expiration)) {
            return ".bmp";
        }

        return ".jpg";
    }

    public static void main(String[] args) {
        System.out.println(
                FileUtils.getPicExpiration("http://store.is.autonavi.com/showpic/aa.gif3"));
    }
}
