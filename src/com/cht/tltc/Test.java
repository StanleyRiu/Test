package com.cht.tltc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Test {

	public Test() {
/*
	In Java, we can use ProcessBuilder or 
	Runtime.getRuntime().exec 
	to execute external shell command :
*/
	}

	public static void main(String[] args) throws IOException {
		new Test().zipFile();
	}

	void zipFile() throws IOException {
		String sourceDir = "D:\\C23\\OSS開源軟體檢測\\0723_高雄航空站人事差勤管理系統\\[OSS][01]高雄航空站人事差勤管理系統\\KIAWeb";
		String destDir = "D:\\C23\\OSS開源軟體檢測\\0723_高雄航空站人事差勤管理系統\\[OSS][01]高雄航空站人事差勤管理系統";
		File sourceFiles = new File(sourceDir);
		
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(destDir, "reports.zip")));
		
		for (File f : sourceFiles.listFiles()) {
			checkFileType(f, zos, f.getName());
		}
		zos.finish();
		zos.close();
		System.out.println("done");
	}

	void checkFileType(File f, ZipOutputStream zos, String zeName) throws IOException {
		if (f.isDirectory()) {
			for (File file : f.listFiles()) {
				checkFileType(file, zos, zeName+File.separator+file.getName());
			}
		} else {
			addZipFile(f, zos, zeName);
		}
	}
	void addZipFile(File f, ZipOutputStream zos, String zeName) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		int l;
		byte[] b = new byte[(int) f.length()];
		System.out.println("processing..."+zeName+", file length: "+f.length()+", b length: "+b.length);
		ZipEntry ze = new ZipEntry(zeName);
		zos.putNextEntry(ze);
		do {
			l=fis.read(b);
			System.out.println(l);
			if (l!=-1) zos.write(b, 0, l);
		} while (l != -1 && f.length()!=0);
		fis.close();
	}
	
	void timer() {
		Timer timer = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Task 執行時間：" + new Date());
			}
		};
		System.out.println("Task 執行時間：" + new Date());
		timer.schedule(tt, 3000, 2000);	
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("Task 執行時間：" + new Date());
	}

	void doProcessBuilder() {
		ProcessBuilder pb = new ProcessBuilder();
		pb.command("cmd.exe", "/c", "dir");
		try {
			Process process = pb.start();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line+System.lineSeparator());
			}
			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println(sb);
				System.exit(0);
			} else {
				
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void doRun() {
		try { 
            // Command to create an external process 
            String command = null;
            command = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"; 
            command = "cmd.exe /c dir";
            // Running the above command 
            Runtime run  = Runtime.getRuntime(); 
            Process proc = run.exec(command);
            
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            
            System.out.println("Output of running is:");
            while ((line = br.readLine()) != null) {
            	System.out.println(line);
            }
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
	}
	
	private void doRuntime() throws IOException, InterruptedException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		String homeDirectory = System.getProperty("user.home");
		Process process;
		if (isWindows) {
		    process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory));
		} else {
		    process = Runtime.getRuntime().exec(String.format("sh -c ls %s", homeDirectory));
		}
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;
	}

	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;
	 
	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }
	 
	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
	    }
	}
}