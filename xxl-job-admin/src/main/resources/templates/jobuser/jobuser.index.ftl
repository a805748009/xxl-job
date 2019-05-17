<!DOCTYPE html>
<html>
<head>
  	<#import "../common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
    <title>${I18n.user_manager}</title>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
    <!-- header -->
	<@netCommon.commonHeader />
    <!-- left -->
	<@netCommon.commonLeft "jobuser" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>${I18n.user_manager}</h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">${I18n.user_list}</h3>&nbsp;&nbsp;
                            <button class="btn btn-info btn-xs pull-left2 add" >${I18n.user_add}</button>
                        </div>
                        <div class="box-body">
                            <table id="joblog_list" class="table table-bordered table-striped display" width="100%" >
                                <thead>
                                <tr>
                                    <th name="order" >${I18n.user_login_username}</th>
                                    <th name="title" >${I18n.user_authority}</th>
                                    <th name="addressType" >${I18n.user_last_login_time}</th>
                                    <th name="operate" >${I18n.system_opt}</th>
                                </tr>
                                </thead>
                                <tbody>
								<#if list?exists && list?size gt 0>
								<#list list as user>
									<tr>
                                        <td>${user.userName}</td>
                                        <td><#if user.authority>${I18n.user_authority_1}<#else>${I18n.user_authority_0}</#if></td>
                                        <td>${user.lastLoginTime}</td>

                                        <td>
                                            <button class="btn btn-warning btn-xs update"
                                                    userName="${user.userName}"
                                                    authority="${user.authority?string("1","0")}"
                                                    password=" "
                                            >${I18n.system_opt_edit}</button>
                                            <button class="btn btn-danger btn-xs remove" username="${user.userName}" >${I18n.system_opt_del}</button>
                                        </td>
                                    </tr>
                                </#list>
                                </#if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- 新增.模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" >${I18n.user_add}</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form" >
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">${I18n.user_login_username}<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="userName" placeholder="${I18n.system_please_input}${I18n.user_login_username}" maxlength="64" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">${I18n.user_login_password}<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="password" placeholder="${I18n.system_please_input}${I18n.user_login_password}" maxlength="12" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">${I18n.user_authority}<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="radio" name="authority" value="0" checked />${I18n.user_authority_0}
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" name="authority" value="1" />${I18n.user_authority_1}
                            </div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary"  >${I18n.system_save}</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">${I18n.system_cancel}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- 更新.模态框 -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" >${I18n.user_update}</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form" >
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">${I18n.user_login_username}<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="userName" placeholder="${I18n.system_please_input}${I18n.user_login_username}" maxlength="64" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">${I18n.user_login_password}<font color="red">*</font></label>
                            <div class="col-sm-10"><input type="text" class="form-control" name="password" placeholder="${I18n.system_please_input}${I18n.user_login_password}" maxlength="12" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">${I18n.user_authority}<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="radio" name="authority" value="0" checked />${I18n.user_authority_0}
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" name="authority" value="1" />${I18n.user_authority_1}
                            </div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary"  >${I18n.system_save}</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">${I18n.system_cancel}</button>
                                <input type="hidden" name="id" >
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- footer -->
	<@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<#-- jquery.validate -->
<script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>
<script src="${request.contextPath}/static/js/jobuser.index.1.js"></script>
</body>
</html>
