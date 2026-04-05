<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section style="margin-bottom: 24px; padding: 12px; border: 1px solid #ccc; border-radius: 8px; background: #fafafa;">
	<h3 style="margin-top: 0;">Tổng quan của bạn</h3>

	<c:if test="${not empty dashSummaryError}">
		<p style="color: red;">${dashSummaryError}</p>
	</c:if>

	<div style="display: flex; flex-wrap: wrap; gap: 16px; margin-bottom: 20px;">
		<div style="padding: 12px; background: #fff; border: 1px solid #ddd; border-radius: 6px; min-width: 160px;">
			<div style="font-size: 0.85rem; color: #555;">Phép năm còn lại</div>
			<div style="font-size: 1.5rem; font-weight: bold;">${empAnnualRemaining}</div>
		</div>
		<div style="padding: 12px; background: #fff; border: 1px solid #ddd; border-radius: 6px; min-width: 160px;">
			<div style="font-size: 0.85rem; color: #555;">Đơn chờ duyệt</div>
			<div style="font-size: 1.5rem; font-weight: bold;">${empPendingCount}</div>
		</div>
		<div style="padding: 12px; background: #fff; border: 1px solid #ddd; border-radius: 6px; min-width: 200px;">
			<div style="font-size: 0.85rem; color: #555;">Đồng nghiệp đang nghỉ hôm nay</div>
			<div style="font-size: 1.5rem; font-weight: bold;">${empTeamOutToday}</div>
			<c:if test="${not empty empTeamOutNames}">
				<div style="font-size: 0.8rem; margin-top: 6px; color: #444;">
					<c:forEach var="nm" items="${empTeamOutNames}" varStatus="st">
						<c:if test="${st.index < 8}">
							<c:if test="${st.index > 0}">, </c:if>
							<c:out value="${nm}" />
						</c:if>
					</c:forEach>
					<c:if test="${fn:length(empTeamOutNames) > 8}"> …</c:if>
				</div>
			</c:if>
		</div>
		<div style="padding: 12px; background: #fff; border: 1px solid #ddd; border-radius: 6px; min-width: 240px; flex: 1;">
			<div style="font-size: 0.85rem; color: #555;">Lần nghỉ gần nhất</div>
			<div><strong>${empLastLeavePeriod}</strong> — <strong>${empLastLeaveDays}</strong> ngày</div>
			<div style="font-size: 0.9rem; margin-top: 4px;"><c:out value="${empLastLeaveReason}" /></div>
		</div>
	</div>

	<div style="display: flex; flex-wrap: wrap; gap: 24px; align-items: flex-start;">
		<div style="flex: 0 0 280px;">
			<h4 style="margin-top: 0;">Quỹ phép năm</h4>
			<canvas id="empBalanceChart" width="260" height="260"></canvas>
			<p style="font-size: 0.85rem; color: #555;">Tổng: ${empAnnualTotal} &nbsp;|&nbsp; Đã dùng: ${empAnnualUsed}</p>
		</div>
		<div style="flex: 1; min-width: 280px;">
			<h4 style="margin-top: 0;">Đơn gần đây</h4>
			<table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%;">
				<tr style="background: #eee;">
					<th>Loại (nhóm lý do)</th>
					<th>Thời gian</th>
					<th>Số ngày</th>
					<th>Trạng thái</th>
				</tr>
				<c:forEach var="row" items="${empRecentRows}">
					<tr>
						<td><c:out value="${row['type']}" /></td>
						<td><c:out value="${row['period']}" /></td>
						<td>${row['duration']}</td>
						<td>${row['status']}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty empRecentRows}">
					<tr><td colspan="4">Chưa có đơn nghỉ.</td></tr>
				</c:if>
			</table>
		</div>
	</div>

	<h4 style="margin-top: 24px;">Thông báo gần đây</h4>
	<ul>
		<c:forEach var="note" items="${notificationPreview}">
			<li style="margin-bottom: 6px;">
				<c:if test="${not note.read}"><strong>[Chưa đọc]</strong> </c:if>
				<c:out value="${note.content}" />
				<c:if test="${note.sentTime != null}">
					<small style="color: #666;"> — <fmt:formatDate value="${note.sentTime}" pattern="yyyy-MM-dd HH:mm" /></small>
				</c:if>
			</li>
		</c:forEach>
		<c:if test="${empty notificationPreview}">
			<li>Không có thông báo.</li>
		</c:if>
	</ul>
	<p><a href="${pageContext.request.contextPath}/notifications">Xem tất cả thông báo</a></p>
</section>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
<script>
(function () {
	const used = ${empAnnualUsed};
	const rem = ${empAnnualRemaining};
	const ctx = document.getElementById('empBalanceChart');
	if (ctx && typeof Chart !== 'undefined') {
		new Chart(ctx, {
			type: 'doughnut',
			data: {
				labels: ['Đã dùng', 'Còn lại'],
				datasets: [{
					data: [used, rem],
					backgroundColor: ['#e74c3c', '#27ae60']
				}]
			},
			options: {
				responsive: true,
				plugins: { legend: { position: 'bottom' } }
			}
		});
	}
})();
</script>
