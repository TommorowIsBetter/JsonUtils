package com.plase.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.plase.entity.ThinkTimeBean;

/**
 * 
 * @author wangyan
 * @date 2019年3月11日
 * @Description 
 * @version 2019年3月11日
 */
public class Util {

	static String filepath = "E:\\json";
	static String  test= "E:\\a.json";
	static String aimpath = "E:\\jsontest";
	static String name = "test.json";
	static String deletePath = "E:\\jsontest";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//MergeJsons(filepath, aimpath, name);
		//File deletePath = new File("E:\\jsontest");
		//deleteFile(deletePath);
		//prettyJson(test);
		
	}
	
	
	/**
	 * 
	 * @param filepath 待处理的文件夹
	 * @param aimPath 目的存入的指定文件夹
	 * @param name 重新给生成的json文件进行命名
	 * @throws IOException
	 * @Description 把一个文件夹下的所有json文件变成一个json文件，然后存储在指定的文件夹中。
	 */
	public static void MergeJsons(String filepath, String aimPath, String name) throws IOException{
		File baseFile = new File(filepath); //File类型可以是文件也可以是文件夹
		File[] fileList = baseFile.listFiles(); //将该目录下的所有文件放置在一个File类型的数组中
		List<ThinkTimeBean> thinkTimeBeanList = new ArrayList<ThinkTimeBean>();//建立一个结合用于存放待会获取json的对象
		if (fileList == null){    //空指针进行处理
			System.out.println("filepath doesn't exist!");
		}else {
		for (int i = 0; i < fileList.length; i++) {
			String str = FileUtils.readFileToString(fileList[i], "utf-8");//把json文件转换为字符串
			try{
				ThinkTimeBean thinkTimeBean = JSON.parseObject(str, ThinkTimeBean.class);//把字符串转换为实体类
				thinkTimeBeanList.add(thinkTimeBean);//json实体类加入到集合中
			}catch(Exception e){
				System.out.println("the format of file is wrong!");
			}
		  }
		}
		File fileDirectory = new File(aimpath);
		if (!fileDirectory.exists()){
			fileDirectory.mkdirs();
		}
		File file = new File(aimpath + "/" + name);
		if (!file.exists()){
			file.createNewFile();
		}
		String mergeJson = JSON.toJSONString(thinkTimeBeanList,true);//把json实体类集合转换为字符串,这里加入true即格式化Json字符串
		FileUtils.writeStringToFile(file, mergeJson, "utf-8");//把字符串写入到文件中
	}
	

	/**
	 * 
	 * @param file 待删除的文件夹路径
	 * @Description 删除指定文件夹的文件及其子文件
	 */
	public static void deleteFile(File file){
		if (file.isFile()){//判断是否为文件，是，则删除
			System.out.println(file.getAbsolutePath());//打印路径
			file.delete();
		}else{//不为文件则为文件夹
			String[] childFilePath = file.list();//获取文件夹下的所有文件相对路径
			for (String path:childFilePath){
				File childFile = new File(file.getAbsoluteFile() + "/" + path);
				deleteFile(childFile);//递归，对每个都进行判断
			}
			System.out.println(file.getAbsolutePath());
			file.delete();
		}
	}
	
	

	/**
	 * 
	 * @param filePath 读取文件的路径
	 * @return 返回json文件的字符串
	 * @Description 读取一个json文件，然后字符串的形式返回。
	 */
	public static String readJson(String filePath) {
		BufferedReader reader = null;
        StringBuffer bf = new StringBuffer();
        try{
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                bf.append(tempString);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bf.toString();
    }
	
	
	/**
	 * 
	 * @param filePath 读取文件的位置绝对路径。
	 * @throws IOException
	 * @Description 读取一个json文件，然后一个格式化的形式打印出json文件的内容。此时格式化后的字符串写入新的json文件中也可以。
	 */
	public static void prettyJson(String filePath) throws IOException{
		String str = readJson(filePath);
		System.out.println(str);//打印读取的json文件
		List<ThinkTimeBean> list = JSON.parseArray(str,ThinkTimeBean.class);//把json字符串转换为对象列表，因为这里的为json数组。
		String str1 = JSON.toJSONString(list,true);//把json对象转换为字符串，并且格式化(多加一个true参数即可)
		System.out.println(str1); //打印格式化后的字符串
	}
}

