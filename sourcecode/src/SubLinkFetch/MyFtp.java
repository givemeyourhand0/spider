package SubLinkFetch;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import sun.misc.Queue;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class MyFtp {
	 /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String username,
                               String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            //jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sftp;
    }
    /**
     * 上传文件夹
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件夹目录
     * @param sftp
     */
    public void upload(String directory, String uploadFile, ChannelSftp sftp) 
    {
    	int i=0;
        try {
//            sftp.cd(directory);
//           // sftp.cd(uploadFile);
//            System.out.println((new File(directory)).getPath());
            File file = new File(uploadFile);
//            System.out.println(file.getPath());
//            File[] list=file.listFiles();  //列出File对象的所有子文件和路径，返回File数组
//            for(i=0;i<list.length;i++)
//            {
//            sftp.put(new FileInputStream(list[i]),list[i].getName());
//            }
            System.out.println("Upload Success!");
           System.out.println(delAllFile(file.getAbsolutePath()));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
 // 删除指定文件夹下所有文件
 // param path 文件夹完整绝对路径
 public static boolean delAllFile(String path) {
    boolean flag = false;
    File file = new File(path);
    if (!file.exists()) {
     return flag;
    }
    if (!file.isDirectory()) {
     return flag;
    }
    String[] tempList = file.list();
    File temp = null;
    for (int i = 0; i < tempList.length; i++) {
     if (path.endsWith(File.separator)) {
      temp = new File(path + tempList[i]);
     } else {
 temp = new File(path + File.separator + tempList[i]);
     }
     if (temp.isFile()) {
      temp.delete();
     }
     if (temp.isDirectory()) {
        delAllFile(path + "/" + tempList[i]);// 删除文件夹里面的文件
      flag = true;
     }
    }
    return flag;
 }
  
    /** 
     * 判断目录是否存在 
     * @param directory 
     * @return 
     */  
    public boolean isDirExist(String directory,ChannelSftp sftp)  
    {  
        boolean isDirExistFlag = false;  
        try  
        {  
            SftpATTRS sftpATTRS = sftp.lstat(directory);  
            isDirExistFlag = true;  
            return sftpATTRS.isDir();  
        }  
        catch (Exception e)  
        {  
            if (e.getMessage().toLowerCase().equals("no such file"))  
            {  
                isDirExistFlag = false;  
            }  
        }  
        return isDirExistFlag;  
    }  
  
    /** 
     * 如果目录不存在就创建目录 
     * @param path 
     */  
    public void mkdirs(String path)  
    {  
        File f = new File(path);  
  
        String fs = f.getParent();  
  
        f = new File(fs);  
  
        if (!f.exists())  
        {  
            f.mkdirs();  
        }  
    }  
    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException 
    {
        return sftp.ls(directory);
    }
    
    //ChannelSftp c = (ChannelSftp) channel;  c.mkdir("/user/app/image");
    public static void main(String[] args) throws SftpException {
        MyFtp sf = new MyFtp();
        String host = "222.25.176.34";
        int port = 22;
        String username = "root";
        String password = "111111";
        ChannelSftp sftp = sf.connect(host, port, username, password);
        //sf.upload("/opt/BaQiaoGovImage/", "BaQiaoGovImage", sftp);
        sf.upload("/opt/XianYangGovImage/", "D:\\文件娱乐\\文件和照片\\2017spring\\智库\\XianYangGovImage\\", sftp);
      //File  file=new File("C:\\Users\\WYL-PC\\Desktop\\BaQiaoGovImage");  
     //System.out.print(file.delete() ); sf.upload("/opt/BaQiaoGovImage/", "D:\\文件娱乐\\文件和照片\\2017spring\\智库\\BaQiaoGovImage\\", sftp);
       //sf.upload("/opt/BaQiaoGovImage/", "D:\\文件娱乐\\文件和照片\\2017spring\\智库\\BaQiaoGovImage\\", sftp); 
    }
	}