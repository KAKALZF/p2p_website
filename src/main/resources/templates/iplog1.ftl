<#if pageResult.listData?size &gt; 0 > <#list pageResult.listData as
item>
<tr>
	<td>${item.username}</td>
	<td>${item.loginTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	<td>${item.ip}</td>
	<td>${item.stateDisplay}</td>
</tr>
</#list> <#else>
<tr>
	<td colspan="7" align="center">
		<p class="text-danger">目前没有符合要求的标</p>
	</td>
</tr>
</#if>

<script type="text/javascript">
	$function(){
	$("#page_container").empty().append($('<ul id="pagination" class="pagination"></ul>'));
	$('#pagination').twbsPagination({
		totalPages :${pageResult.totalPage},
		visiblePages : 5,
		startPage:${pageResult.currentPage},
		onPageClick : function(event, page) {
			$("#currentPage").val(page);
			$("#searchForm").submit();
		}
	});
}
</script>