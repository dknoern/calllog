package com.slalom.calllog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallLogDatabase {

	private static final Logger logger = LoggerFactory
			.getLogger(CallLogDatabase.class);

	private String[] topics = { "Call registration (website)", "Professional",
			"Resource Referral", "Family Issues", "Financial Navigation",
			"Information", "Vent/Talk" };
	private String[] literatures = { "Mail Catalog", "Email Catalog",
			"E-newsletter", "Other CL Information" };
	private String[] callerIsOptions = { "Patient", "Sibling", "Paren",
			"Unknown" };
	private String[] callStatusOptions = { "New", "Repeat", "On-going",
			"Unknown" };
	private String[] referralOptions = { "211", "American Cancer Society",
			"Cancer Information Services",
			"Cancer Lifeline Financial Navigation",
			"Patient's Physician/Hospital Worker",
			"Senior Information and Assistance" };
	private String[] leadSourceOptions = { "Hospital", "Friend or Family",
			"Cancer Lifeline Printed Media (Flyer, Bookmark, Catalog)",
			"Cancer Lifeline Website", "Google", "Facebook", "Twitter",
			"Paper or Online Phone Book" };

	@Autowired
	DataSource dataSource;

	public List<CallLog> getCallLogs(int page) {

		List<CallLog> list = new ArrayList<CallLog>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			stmt = conn
					.prepareStatement("select id, call_date,call_description,caller_first_name,"
							+ "caller_last_name,caller_phone,caller_zip,length_of_call,mail_address1,mail_address2,mail_city,mail_email,"
							+ "mail_state,mail_zip,quotes_and_anecdotal_information,version,volunteer_id,call_status,lead_source,"
							+ "caller_relation,referral_type from call_log order by id desc");
			rs = stmt.executeQuery();
			int i = 0;

			while (rs.next() && i < (page) * 10) {

				if (i >= (page - 1) * 10) {
					CallLog callLog = new CallLog();
					callLog.setId(rs.getInt("id"));
					callLog.setVolunteerId(rs.getString("volunteer_id"));
					callLog.setCallDate(rs.getString("call_date"));
					callLog.setCallerIs(rs.getString("caller_relation"));
					callLog.setCallStatus(rs.getString("call_status"));
					list.add(callLog);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanup(conn, stmt, rs);
		}
		return list;

	}

	public int countRows() throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int rows = 0;
		try {
			conn = dataSource.getConnection();
			stmt = conn
					.prepareStatement("select count(*) as cnt from call_log");
			rs = stmt.executeQuery();
			rs.next();
			rows = rs.getInt("cnt");
		} catch (Exception e) {
		} finally {
			cleanup(conn, stmt, rs);
		}
		return rows;
	}

	private void cleanup(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ignore) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ignore) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ignore) {
			}
		}
	}

	public synchronized int saveRecord(CallLog callLog) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int callId = callLog.getId();
		boolean newRecord = false;

		try {
			conn = dataSource.getConnection();

			if (callId == 0) {
				newRecord = true;

				stmt = conn.prepareStatement("select max(id) from call_log");
				rs = stmt.executeQuery();

				if (rs.next()) {
					callId = rs.getInt(1) + 1;
				} else {
				}
			}

			String callDateTime = callLog.getCallDate() + " "
					+ callLog.getCallHour() + ":" + callLog.getCallMinute()
					+ " " + callLog.getCallAmPm();

			String sql = null;
			if (newRecord) {
				sql = "INSERT INTO call_log (call_date,call_description,caller_first_name,"
						+ "caller_last_name,caller_phone,caller_zip,length_of_call,mail_address1,mail_address2,mail_city,mail_email,"
						+ "mail_state,mail_zip,quotes_and_anecdotal_information,version,volunteer_id,call_status,lead_source,"
						+ "caller_relation,referral_type, id) VALUES ("
						+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?,?,?,?,?)";
			} else {
				sql = "UPDATE call_log set call_date=?,call_description=?,caller_first_name=?,"
						+ "caller_last_name=?,caller_phone=?,caller_zip=?,length_of_call=?,mail_address1=?,mail_address2=?,mail_city=?,mail_email=?,"
						+ "mail_state=?,mail_zip=?,quotes_and_anecdotal_information=?,volunteer_id=?,call_status=?,lead_source=?,"
						+ "caller_relation=?,referral_type=? where id=?";
			}

			int i = 1;

			String callerRelation = getValueOrOtherValue(callLog.getCallerIs(),
					callLog.getCallerIsOther());
			String callStatus = getValueOrOtherValue(callLog.getCallStatus(),
					callLog.getCallStatusOther());
			String referral = getValueOrOtherValue(callLog.getReferral(),
					callLog.getReferralOther());
			String leadSource = getValueOrOtherValue(callLog.getLeadSource(),
					callLog.getLeadSourceOther());

			int lengthOfCall = Integer.parseInt(callLog.getLengthOfCall());

			Timestamp timestamp = new Timestamp(new SimpleDateFormat(
					"MM/dd/yyyy hh:mm a").parse(callDateTime).getTime());

			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(i++, timestamp);
			stmt.setString(i++, callLog.getDescription());
			stmt.setString(i++, callLog.getFirstName());
			stmt.setString(i++, callLog.getLastName());
			stmt.setString(i++, callLog.getCallerPhoneNumber());

			stmt.setString(i++, callLog.getCallerZip());
			stmt.setInt(i++, lengthOfCall);
			stmt.setString(i++, callLog.getContactAddress1());
			stmt.setString(i++, callLog.getContactAddress2());
			stmt.setString(i++, callLog.getContactCity());
			stmt.setString(i++, callLog.getContactEmail());
			stmt.setString(i++, callLog.getContactState());
			stmt.setString(i++, callLog.getContactZip());

			stmt.setString(i++, callLog.getQuotes());
			stmt.setString(i++, callLog.getVolunteerId());
			stmt.setString(i++, callStatus);
			stmt.setString(i++, leadSource);
			stmt.setString(i++, callerRelation);
			stmt.setString(i++, referral);

			stmt.setInt(i++, callId);

			stmt.executeUpdate();

			if (!newRecord) {
				stmt = conn
						.prepareStatement("delete from call_topic where id=?");
				stmt.setInt(1, callId);
				stmt.executeUpdate();
				stmt = conn
						.prepareStatement("delete from call_literature where id=?");
				stmt.setInt(1, callId);
				stmt.executeUpdate();
			}

			if (callLog.getCallTopic() != null
					&& callLog.getCallTopic().size() > 0) {
				stmt = conn
						.prepareStatement("insert into call_topic (id,topic) values(?,?)");
				for (String topic : callLog.getCallTopic()) {
					if ("Other".equals(topic)) {
						if (callLog.getCallTopicOther() != null
								&& callLog.getCallTopicOther().length() > 0) {
							stmt.setInt(1, callId);
							stmt.setString(2, callLog.getCallTopicOther());
							stmt.executeUpdate();
						}
					} else {
						stmt.setInt(1, callId);
						stmt.setString(2, topic);
						stmt.executeUpdate();
					}
				}
			}

			if (callLog.getLiterature() != null
					&& callLog.getLiterature().size() > 0) {
				stmt = conn
						.prepareStatement("insert into call_literature (id,literature) values(?,?)");
				for (String literature : callLog.getLiterature()) {
					if ("Other".equals(literature)) {
						if (callLog.getLiteratureOther() != null
								&& callLog.getLiteratureOther().length() > 0) {
							stmt.setInt(1, callId);
							stmt.setString(2, callLog.getLiteratureOther());
							stmt.executeUpdate();
						}
					} else {
						stmt.setInt(1, callId);
						stmt.setString(2, literature);
						stmt.executeUpdate();
					}
				}
			}

			logger.info("record " + callId + " saved");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanup(conn, stmt, rs);
		}
		return callId;
	}

	private String getValueOrOtherValue(String value, String otherValue) {
		String result = value;
		if ("Other".equals(value)) {
			if (otherValue != null && otherValue.length() > 0) {
				result = otherValue;
			} else {
				result = null;
			}
		}
		return result;
	}

	public CallLog getCallLog(int callId) {

		CallLog callLog = new CallLog();
		callLog.setId(callId);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			String sql = "select call_date,call_description,caller_first_name,"
					+ "caller_last_name,caller_phone,caller_zip,length_of_call,mail_address1,mail_address2,mail_city,mail_email,"
					+ "mail_state,mail_zip,quotes_and_anecdotal_information,version,volunteer_id,call_status,lead_source,"
					+ "caller_relation,referral_type from call_log where id=?";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, callId);

			rs = stmt.executeQuery();

			if (rs.next()) {

				Date callDate = new Date(rs.getTimestamp("call_date").getTime());

				callLog.setCallDate(new SimpleDateFormat("MM/dd/yyyy")
						.format(callDate));
				callLog.setCallHour(new SimpleDateFormat("hh").format(callDate));
				callLog.setCallMinute(new SimpleDateFormat("mm")
						.format(callDate));
				callLog.setCallAmPm(new SimpleDateFormat("a").format(callDate));

				callLog.setDescription(rs.getString("call_description"));
				callLog.setFirstName(rs.getString("caller_first_name"));
				callLog.setLastName(rs.getString("caller_last_name"));
				callLog.setCallerPhoneNumber(rs.getString("caller_phone"));

				callLog.setCallerZip(rs.getString("caller_zip"));
				callLog.setLengthOfCall(Integer.toString(rs
						.getInt("length_of_call")));
				callLog.setContactAddress1(rs.getString("mail_address1"));
				callLog.setContactAddress2(rs.getString("mail_address2"));
				callLog.setContactCity(rs.getString("mail_city"));
				callLog.setContactEmail(rs.getString("mail_email"));
				callLog.setContactState(rs.getString("mail_state"));
				callLog.setContactZip(rs.getString("mail_zip"));

				callLog.setQuotes(rs
						.getString("quotes_and_anecdotal_information"));
				callLog.setVolunteerId(rs.getString("volunteer_id"));

				String callStatus = rs.getString("call_status");
				if (contains(callStatusOptions, callStatus)) {
					callLog.setCallStatus(callStatus);
				} else if (callStatus != null && callStatus.length() > 0) {
					callLog.setCallStatus("Other");
					callLog.setCallStatusOther(callStatus);
				}

				String leadSource = rs.getString("lead_source");
				if (contains(leadSourceOptions, leadSource)) {
					callLog.setLeadSource(leadSource);
				} else if (leadSource != null && leadSource.length() > 0) {
					callLog.setLeadSource("Other");
					callLog.setLeadSourceOther(leadSource);
				}

				String callerIs = rs.getString("caller_relation");
				if (contains(callerIsOptions, callerIs)) {
					callLog.setCallerIs(callerIs);
				} else if (callerIs != null && callerIs.length() > 0) {
					callLog.setCallerIs("Other");
					callLog.setCallerIsOther(callerIs);
				}

				String referral = rs.getString("referral_type");
				if (contains(referralOptions, referral)) {
					callLog.setReferral(referral);
				} else if (referral != null && referral.length() > 0) {
					callLog.setReferral("Other");
					callLog.setReferralOther(referral);
				}
			}

			stmt = conn
					.prepareStatement("select topic from call_topic where id=?");
			stmt.setInt(1, callId);
			rs = stmt.executeQuery();
			Set<String> callTopics = new HashSet<String>();
			while (rs.next()) {
				String topic = rs.getString("topic");
				if (contains(topics, topic)) {
					callTopics.add(topic);
				} else {
					callTopics.add("Other");
					callLog.setCallTopicOther(topic);
				}
			}
			callLog.setCallTopic(callTopics);

			stmt = conn
					.prepareStatement("select literature from call_literature where id=?");
			stmt.setInt(1, callId);
			rs = stmt.executeQuery();
			Set<String> callLiteratures = new HashSet<String>();
			while (rs.next()) {
				String literature = rs.getString("literature");
				if (contains(literatures, literature)) {
					callLiteratures.add(literature);
				} else {
					callLiteratures.add("Other");
					callLog.setLiteratureOther(literature);
				}
			}
			callLog.setLiterature(callLiteratures);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			cleanup(conn, stmt, rs);
		}
		return callLog;

	}

	private static boolean contains(String[] array, String s) {
		for (String v : array) {
			if (v.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public Volunteer getVolunteer(String username) {

		Volunteer volunteer = new Volunteer();

		volunteer.setUsername(username);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			String sql = "select first_name, last_name, id, password, role, enabled from volunteer where username=?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);

			rs = stmt.executeQuery();

			if (rs.next()) {

				volunteer.setFirstName(rs.getString("first_name"));
				volunteer.setLastName(rs.getString("last_name"));
				volunteer.setVolunteerId(rs.getString("id"));
				volunteer.setPassword(rs.getString("password"));
				volunteer.setRole(rs.getString("role"));
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			cleanup(conn, stmt, rs);
		}
		return volunteer;

	}

}
