<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section style="margin-bottom: 24px; padding: 12px; border: 1px solid #ccc; border-radius: 8px; background: #fafafa;">
	<h3 style="margin-top: 0;">Tổng quan hệ thống</h3>

	<c:if test="${not empty dashSummaryError}">
		<p style="color: red;">${dashSummaryError}</p>
	</c:if>

	<p style="font-size: 1.05rem;">
		<strong>Tổng số đơn:</strong> ${summaryTotal}
		&nbsp;|&nbsp; <strong>Chờ duyệt:</strong> ${summaryPending}
		&nbsp;|&nbsp; <strong>Đã duyệt:</strong> ${summaryApproved}
		&nbsp;|&nbsp; <strong>Từ chối:</strong> ${summaryRejected}
	</p>

	<div style="display: flex; flex-wrap: wrap; gap: 24px; margin-top: 16px;">
		<div style="flex: 1; min-width: 300px; max-width: 520px;">
			<h4 style="margin-bottom: 8px;">Xu hướng nghỉ (8 tuần gần nhất)</h4>
			<canvas id="dashTrendChart" height="220"></canvas>
		</div>
		<div style="flex: 1; min-width: 280px; max-width: 420px;">
			<h4 style="margin-bottom: 8px;">Theo loại nghỉ (Leave type)</h4>
			<canvas id="dashReasonChart" height="220"></canvas>
		</div>
	</div>

	<h4 style="margin-top: 24px;">Đang nghỉ trong tuần này (đã duyệt)</h4>
	<table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%; max-width: 900px;">
		<tr style="background: #eee;">
			<th>Nhân sự</th>
			<th>Từ</th>
			<th>Đến</th>
			<th>Lý do</th>
		</tr>
		<c:forEach var="lr" items="${onLeaveThisWeek}">
			<tr>
				<td>
					<c:choose>
						<c:when test="${lr.user == null}">—</c:when>
						<c:when test="${lr.user.fullName != null && fn:length(lr.user.fullName) > 0}">
							<c:out value="${lr.user.fullName}" />
						</c:when>
						<c:otherwise>
							<c:out value="${lr.user.username}" />
						</c:otherwise>
					</c:choose>
				</td>
				<td>${lr.startDate}</td>
				<td>${lr.endDate}</td>
				<td><c:out value="${lr.reason}" /></td>
			</tr>
		</c:forEach>
		<c:if test="${empty onLeaveThisWeek}">
			<tr><td colspan="4">Không có ai đang nghỉ trong tuần (theo đơn đã duyệt).</td></tr>
		</c:if>
	</table>

	<h4 style="margin-top: 20px;">Thông báo gần đây</h4>
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
	const trendLabels = [
		<c:forEach var="l" items="${chartTrendLabels}" varStatus="st">
			<c:if test="${!st.first}">,</c:if>"<c:out value="${l}" />"
		</c:forEach>
	];
	const trendData = [
		<c:forEach var="v" items="${chartTrendData}" varStatus="st">
			<c:if test="${!st.first}">,</c:if>${v}
		</c:forEach>
	];
	const reasonLabels = [
		<c:forEach var="rl" items="${chartReasonLabels}" varStatus="st">
			<c:if test="${!st.first}">,</c:if>"<c:out value="${rl}" />"
		</c:forEach>
	];
	const reasonData = [
		<c:forEach var="rd" items="${chartReasonData}" varStatus="st">
			<c:if test="${!st.first}">,</c:if>${rd}
		</c:forEach>
	];

	const trendCtx = document.getElementById('dashTrendChart');
	if (trendCtx && typeof Chart !== 'undefined') {
		new Chart(trendCtx, {
			type: 'line',
			data: {
				labels: trendLabels,
				datasets: [{
					label: 'Số đơn',
					data: trendData,
					borderColor: '#2980b9',
					backgroundColor: 'rgba(41, 128, 185, 0.15)',
					fill: true,
					tension: 0.2
				}]
			},
			options: {
				responsive: true,
				plugins: { legend: { display: false } },
				scales: { x: { ticks: { maxRotation: 45, minRotation: 45 } } }
			}
		});
	}

	const reasonCtx = document.getElementById('dashReasonChart');
	if (reasonCtx && typeof Chart !== 'undefined') {
		if (reasonLabels.length === 0) {
			reasonCtx.parentElement.appendChild(document.createElement('p')).textContent = 'Chưa có dữ liệu để vẽ biểu đồ.';
		} else {
			const palette = ['#3498db', '#9b59b6', '#1abc9c', '#e67e22', '#e74c3c', '#95a5a6', '#34495e', '#2ecc71',
				'#16a085', '#8e44ad', '#f39c12', '#d35400', '#2980b9', '#c0392b'];
			new Chart(reasonCtx, {
				type: 'doughnut',
				data: {
					labels: reasonLabels,
					datasets: [{
						data: reasonData,
						backgroundColor: reasonLabels.map(function (_, i) { return palette[i % palette.length]; })
					}]
				},
				options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
			});
		}
	}
})();
</script>
