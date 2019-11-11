package vo;

import java.sql.Date;

public class BlogBean {
	private int blog_num;
	private String blog_pass;
	private String blog_subject;
	private String blog_content;
	private String blog_file;
	private int readcount;
	private Date blog_date;
	

	public int getBlog_num() {
		return blog_num;
	}
	public void setBlog_num(int blog_num) {
		this.blog_num = blog_num;
	}
	public String getBlog_pass() {
		return blog_pass;
	}
	public void setBlog_pass(String blog_pass) {
		this.blog_pass = blog_pass;
	}
	public String getBlog_subject() {
		return blog_subject;
	}
	public void setBlog_subject(String blog_subject) {
		this.blog_subject = blog_subject;
	}
	public String getBlog_content() {
		return blog_content;
	}
	public void setBlog_content(String blog_content) {
		this.blog_content = blog_content;
	}
	public String getBlog_file() {
		return blog_file;
	}
	public void setBlog_file(String blog_file) {
		this.blog_file = blog_file;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public Date getBlog_date() {
		return blog_date;
	}
	public void setBlog_date(Date blog_date) {
		this.blog_date = blog_date;
	}
	
	
}
