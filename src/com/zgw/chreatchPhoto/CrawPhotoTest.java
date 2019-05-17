package com.zgw.chreatchPhoto;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/5/15
 * @since 1.0.0
 */
public class CrawPhotoTest {
    // 爬取百度图片
    public static void main(String[] args) throws Exception {
        String downloadPath = "E:\\pic\\baidu";
        List<String> list = nameList("白色图片 黄色图片 灰色图片 青色图片 蓝色图片 紫色图片 粉红图片 橙色图片 绿色图片");//关键字
        getPictures(list, 30, downloadPath); //1代表下载一页，一页一般有30张图片
    }

    // key为关键词,max作为爬取的页数
    public static void getPictures(List<String> keywordList, int max, String downloadPath) throws Exception {
        String gsm = Integer.toHexString(max) + "";
        String finalURL = "";
        String tempPath = "";
        for (String keyword : keywordList) {
            tempPath = downloadPath;
            if (!tempPath.endsWith("\\")) {
                tempPath = downloadPath + "\\";
            }
            tempPath = tempPath + keyword + "\\";
            File f = new File(tempPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            int picCount = 1;
            for (int page = 0; page <= max; page++) {
                sop("正在下载第" + page + "页面");
                Document document = null;
                try {
                    String url = "http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word=" + keyword + "&cg=star&pn=" + page * 100 + "&rn=30&itg=0&z=0&fr=&width=&height=&lm=-1&ic=0&s=0&st=-1&gsm=" + Integer.toHexString(page * 100);
                    sop(url);
                    document = Jsoup.connect(url).data("query", "Java")//请求参数
                            .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")//设置urer-agent  get();
                            .timeout(5000)
                            .get();
                    String xmlSource = document.toString();
                    xmlSource = StringEscapeUtils.unescapeHtml3(xmlSource);
                    sop(xmlSource);
                    String reg = "objURL\":\"http://.+?\\.jpg";
                    Pattern pattern = Pattern.compile(reg);
                    Matcher m = pattern.matcher(xmlSource);
                    while (m.find()) {
                        finalURL = m.group().substring(9);
                        sop(keyword + picCount++ + ":" + finalURL);
                        download(finalURL, tempPath);
                        sop("             下载成功");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sop("下载完毕");
        delMultyFile(downloadPath);//删除所有空图
        sop("已经删除所有空图");
    }

    public static void delMultyFile(String path) {//删除所有空图
        File file = new File(path);
        if (!file.exists())
            throw new RuntimeException("File \"" + path + "\" NotFound when excute the method of delMultyFile()....");
        File[] fileList = file.listFiles();
        File tempFile = null;
        for (File f : fileList) {
            if (f.isDirectory()) {
                delMultyFile(f.getAbsolutePath());
            } else {
                if (f.length() == 0)
                    sop(f.delete() + "---" + f.getName());
            }
        }
    }

    //关键字处理
    public static List<String> nameList(String nameList) {
        List<String> arr = new ArrayList<String>();
        String[] list;
        if (nameList.contains(","))
            list = nameList.split(",");
        else if (nameList.contains("、"))
            list = nameList.split("、");
        else if (nameList.contains(" "))
            list = nameList.split(" ");
        else {
            arr.add(nameList);
            return arr;
        }
        for (String s : list) {
            arr.add(s);
        }
        return arr;
    }

    //打印语句
    public static void sop(Object obj) {
        System.out.println(obj);
    }

    //根据图片网络地址下载图片
    public static void download(String url, String path) {
        //path = path.substring(0,path.length()-2);
        File file = null;
        File dirFile = null;
        FileOutputStream fos = null;
        HttpURLConnection httpCon = null;
        URLConnection con = null;
        URL urlObj = null;
        InputStream in = null;
        byte[] size = new byte[1024];
        int num = 0;
        try {
            String downloadName = url.substring(url.lastIndexOf("/") + 1);
            dirFile = new File(path);
            if (!dirFile.exists() && path.length() > 0) {
                if (dirFile.mkdir()) {
                    sop("creat document file \"" + path.substring(0, path.length() - 1) + "\" success...\n");
                }
            } else {
                file = new File(path + downloadName);
                fos = new FileOutputStream(file);
                if (url.startsWith("http")) {
                    urlObj = new URL(url);
                    con = urlObj.openConnection();
                    httpCon = (HttpURLConnection) con;
                    in = httpCon.getInputStream();
                    while ((num = in.read(size)) != -1) {
                        for (int i = 0; i < num; i++)
                            fos.write(size[i]);
                    }
                }
            }
        } catch (FileNotFoundException notFoundE) {
            sop("找不到该网络图片....");
        } catch (NullPointerException nullPointerE) {
            sop("找不到该网络图片....");
        } catch (IOException ioE) {
            sop("产生IO异常.....");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
