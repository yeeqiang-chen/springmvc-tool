package com.yee.authority.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 *
 */
public class UserVo  extends MisBaseVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     
    private String id;//用户ID、授权号
	private String userName;//用户姓名
    private String userAccount;//系统用户名
    private String pwd;			//密码
    private String deptId;
    private String deptName;
    private String baseId;	//基地id
    private String levelId;	//等级id
    private String userFlag;	//用户标志 1 失效 0 激活
    private String hrAccount;	//人事工号
    private String roleName;
    private String loginTime;
    private String newPass;
    private String idCard;//身份证号码
    private String sex;	//性别
    private String station;//岗位
    private String birthDate;//生日
    private String nation;
    private String goverment;
    private String mobile;
    private String psnclassName;
    private String linkTel;
    private String linkMobile;
    private String uppasswordDate;//最近一次更新密码的时间
	private List<ResourceVo> resources;
	private List<String> roleList;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<ResourceVo> getResources() {
		return resources;
	}
	public void setResources(List<ResourceVo> resources) {
		this.resources = resources;
	}
	
	public boolean hasAuth(String menuId,String auth){
		boolean has = false;
		for(ResourceVo resource:resources){
			if(String.valueOf(resource.getParentId()).equals(menuId) 
			   && auth.equals(resource.getUrlPattern())){
				has = true;
				break;
			}
		}
		return has;
	}
	
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	@Override
	public String toString() {
		return "UserVo [userName=" + userName + ", id=" + id + ", userAccount="
				+ userAccount + ", pwd=" + pwd + "]";
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}
	public String getHrAccount() {
		return hrAccount;
	}
	public void setHrAccount(String hrAccount) {
		this.hrAccount = hrAccount;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getGoverment() {
		return goverment;
	}
	public void setGoverment(String goverment) {
		this.goverment = goverment;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPsnclassName() {
		return psnclassName;
	}
	public void setPsnclassName(String psnclassName) {
		this.psnclassName = psnclassName;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public String getLinkMobile() {
		return linkMobile;
	}
	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}
	public String getUppasswordDate() {
		return uppasswordDate;
	}
	public void setUppasswordDate(String uppasswordDate) {
		this.uppasswordDate = uppasswordDate;
	}
}
