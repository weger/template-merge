package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;

public class TransferUtil {

	public static void main(String[] args) throws Exception {
		// String templateFilePath = "F:\\demo\\source";
		// String templateFileName = "F:\\demo\\config.js";
		// String templateConstName = "Config";
		// boolean clear = true;

		System.out.println("转换开始：" + args[0]);

		boolean clear = false;
		if (args[3].equals("1")) {
			clear = true;
		}

		makeFile(args[1], clear);
		tranfer(args[0], args[1], args[2]);
		System.out.println("转换结束：" + args[1]);
	}

	public static void tranfer(String templateFilePath,
			String templateFileName, String templateConstName) throws Exception {
		File source = new File(templateFilePath);
		File[] fileList = source.listFiles();
		String content = "";
		content += "define({\n";

		for (File f : fileList) {
			if (f.isDirectory()) {
				continue;
			} else {
				content += tranferToString(f, templateConstName);
			}
		}

		content = content.substring(0, content.lastIndexOf(","));

		content += "\n});";

		appendMethodB(templateFileName, content);
	}

	public static File makeFile(String name, boolean clear) {
		File dest = new File(name);

		try {
			if (!dest.exists()) {
				dest.createNewFile();
			} else if (clear && dest.length() > 0) {
				dest.delete();
				dest.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("创建文件失败");
			e.printStackTrace();
		}

		return dest;
	}

	public static String tranferToString(File in, String templateConstName)
			throws Exception {
		StringBuffer content = new StringBuffer();

		String prefix = in.getName()
				.substring(0, in.getName().lastIndexOf("."));

		content.append("    \"").append(prefix).append("\":\'");
		// content.append("[\"").append(prefix).append("\"]='");

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(in), "utf-8"));

			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				content.append(tempString);
			}

			reader.close();
		} catch (Exception e) {
			System.out.println("文件转换出错");
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		content.append("',\n");
		return content.toString();
	}

	/**
	 * A方法追加文件：使用RandomAccessFile
	 */
	public static void appendMethodA(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * B方法追加文件：使用FileWriter
	 */
	public static void appendMethodB(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			Writer writer = new OutputStreamWriter(new FileOutputStream(
					fileName), "UTF-8");
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			System.out.println("文件复制出错");
			e.printStackTrace();
		}
	}

	/**
	 * 文件路径截取
	 */
	public static void tranferPath(String source, String absolutePath,
			String prefix, String suffix) {
		source = source.replace("\\", File.separator);

		if (source.lastIndexOf(".") < 0) {
			absolutePath = source;
			suffix = null;
			prefix = null;
		} else {
			absolutePath = source.substring(0, source.lastIndexOf("\\"));
			suffix = source.substring(source.lastIndexOf(".") + 1,
					source.length());
			prefix = source.substring(source.lastIndexOf("\\") + 1,
					source.lastIndexOf("."));
		}
	}

}