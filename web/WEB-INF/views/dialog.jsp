<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<style type="text/css">
		body { margin: 0px 0px; padding: 0px; text-align:center; }
		table { border-collapse: collapse; }
		#Content 
		{
			width:650px;
			margin: 0px auto;
			text-align: left; /* Counteract to IE5/Win Hack */
			padding: 0px;
			border: 1px dashed #333;
			overflow: auto;
		}	
		.incoming { margin-top: 10px; margin-bottom: 10px; clear: both; }
		.outgoing { margin-top: 10px; margin-bottom: 10px; clear: both; float:right; }
		.precell { border: 1px solid white; max-width: 450px; }
		.cell { border: 1px solid red; }
		.textcell { max-width: 450px; }
		.message { text-align: center; }
		.message table { margin: 0px auto; }
	</style>
</head>
<body>
	<div id="Content">
		<div class="message">
			<table>
				<tr><td class="cell">a</td><td class="cell">b</td><td class="cell">c</td></tr>
				<tr><td class="cell">d</td><td class="call textcell">This is a message.</td><td class="cell">f</td></tr>
				<tr><td class="cell">g</td><td class="cell">h</td><td class="cell">i</td></tr>
			</table>
		</div>
		<div class="incoming">
			<table>
				<tr><td class="cell">a</td><td class="cell">b</td><td class="cell">c</td></tr>
				<tr><td class="cell">d</td><td class="call textcell">This is the text that will display for a message. It might not work right in IE 8 because of the maximum width setting but we can hope it works.</td><td class="cell">f</td></tr>
				<tr><td class="cell">g</td><td class="cell">h</td><td class="cell">i</td></tr>
			</table>
		</div>
		<div class="outgoing">
			<table>
				<tr><td class="precell">a</td><td class="precell">Address goes here</td><td class="precell">c</td></tr>
				<tr><td class="cell">a</td><td class="cell">b</td><td class="cell">c</td></tr>
				<tr><td class="cell">d</td><td class="call textcell">This is the text that will display for a message. It might not work right in IE 8 because of the maximum width setting but we can hope it works.</td><td class="cell">f</td></tr>
				<tr><td class="cell">g</td><td class="cell">h</td><td class="cell">i</td></tr>
			</table>
		</div>
		<div class="outgoing">
			<table>
				<tr><td class="precell">a</td><td class="precell">Address goes here</td><td class="precell">c</td></tr>
				<tr><td class="cell">a</td><td class="cell">b</td><td class="cell">c</td></tr>
				<tr><td class="cell">d</td><td class="call textcell">Short text.</td><td class="cell">f</td></tr>
				<tr><td class="cell">g</td><td class="cell">h</td><td class="cell">i</td></tr>
			</table>
		</div>
		<div class="incoming">
			<table>
				<tr><td class="cell">a</td><td class="cell">b</td><td class="cell">c</td></tr>
				<tr><td class="cell">d</td><td class="call textcell">This is the text that will display for a message. It might not </td><td class="cell">f</td></tr>
				<tr><td class="cell">g</td><td class="cell">h</td><td class="cell">i</td></tr>
			</table>
		</div>
	</div>
</body>
