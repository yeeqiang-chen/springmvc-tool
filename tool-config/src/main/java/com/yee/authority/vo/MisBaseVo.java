package com.yee.authority.vo;

public class MisBaseVo {
	
	protected String eid;//加密id
	
	protected String orderField;//排序字段
	
	protected String orderDirection;//排序顺序
	protected String start="1";
	protected String limit="20";
	
	protected int pageCurrent;
	protected int pageSize;
	protected int pageNum;
	
	protected int total;
	
	protected Long row_id;//序号
	
	private String taskId;//任务节点Id
	private String taskName;//任务节点名称
	private String processId;//流程id
	private String processKey;//流程key
	private String flowVar;//判断流程走向变量
	// 来源编号 来源reportType
	private String sourceReportId;//来源reportID
	private String sourceNumber;//来源编号
	private String sourceReportType;// 来源reportType
	private String targetReportId;
	private String targetReportNum;
	private String targetReportType;
	//权限参数
	protected String userId; //用户ID
	protected String deptId; //部门ID
	private String editType; //操作权限 0 操作本人，1操作本部门 2操作所有
	private int authority; //权限
	public Long getRow_id() {
		return row_id;
	}
	public void setRow_id(Long row_id) {
		this.row_id = row_id;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public int getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eid == null) ? 0 : eid.hashCode());
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result
				+ ((orderDirection == null) ? 0 : orderDirection.hashCode());
		result = prime * result
				+ ((orderField == null) ? 0 : orderField.hashCode());
		result = prime * result + pageCurrent;
		result = prime * result + pageNum;
		result = prime * result + pageSize;
		result = prime * result + ((row_id == null) ? 0 : row_id.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + total;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MisBaseVo other = (MisBaseVo) obj;
		if (eid == null) {
			if (other.eid != null)
				return false;
		} else if (!eid.equals(other.eid))
			return false;
		if (limit == null) {
			if (other.limit != null)
				return false;
		} else if (!limit.equals(other.limit))
			return false;
		if (orderDirection == null) {
			if (other.orderDirection != null)
				return false;
		} else if (!orderDirection.equals(other.orderDirection))
			return false;
		if (orderField == null) {
			if (other.orderField != null)
				return false;
		} else if (!orderField.equals(other.orderField))
			return false;
		if (pageCurrent != other.pageCurrent)
			return false;
		if (pageNum != other.pageNum)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (row_id == null) {
			if (other.row_id != null)
				return false;
		} else if (!row_id.equals(other.row_id))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getFlowVar() {
		return flowVar;
	}
	public void setFlowVar(String flowVar) {
		this.flowVar = flowVar;
	}
	public String getSourceReportId() {
		return sourceReportId;
	}
	public void setSourceReportId(String sourceReportId) {
		this.sourceReportId = sourceReportId;
	}
	public String getSourceNumber() {
		return sourceNumber;
	}
	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}
	public String getSourceReportType() {
		return sourceReportType;
	}
	public void setSourceReportType(String sourceReportType) {
		this.sourceReportType = sourceReportType;
	}
	public String getTargetReportId() {
		return targetReportId;
	}
	public void setTargetReportId(String targetReportId) {
		this.targetReportId = targetReportId;
	}
	public String getTargetReportNum() {
		return targetReportNum;
	}
	public void setTargetReportNum(String targetReportNum) {
		this.targetReportNum = targetReportNum;
	}
	public String getTargetReportType() {
		return targetReportType;
	}
	public void setTargetReportType(String targetReportType) {
		this.targetReportType = targetReportType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
}
