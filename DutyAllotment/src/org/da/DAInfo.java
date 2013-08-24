package org.da;

/**
 * @author Abhishek
 */
public class DAInfo {

    private int m_id;
    private String m_teacherName;
    private String m_dept;
    private String m_email;
    private int [][] m_vigilanceChart;
    private boolean m_isSupervisor;
    private boolean m_hasVigilance = false;

    public DAInfo(int id, String teacherName, String dept, String email, int totalDays, int numSessions) {
        m_id = id;
        m_teacherName = teacherName;
        m_dept = dept;
        m_email = email;
        m_vigilanceChart = new int [totalDays] [numSessions];
    }

    public String getTeacherName() {
        return m_teacherName;
    }

    public String getDept() {
        return m_dept;
    }

    public String getEmail() {
        return m_email;
    }

    public void markVigilance (int dayCount, int sessionNumber, boolean isSupervisor) {
        m_vigilanceChart [dayCount] [sessionNumber] = 1;
        m_isSupervisor = isSupervisor;
        m_hasVigilance = true;
    }

    private boolean hasVigilance () {
        return m_hasVigilance;

    }
    public String printVigilanceInfoSubject () {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Vigilance Allotment Information for " + m_teacherName + " from " + m_dept + " Department");
        return buffer.toString();
    }

    public String printVigilanceInfo () {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Dear " + m_teacherName +",\n");
        if (!hasVigilance()) {
            buffer.append("You do not have any upcoming vigilance");
            return buffer.toString();
        }
        buffer.append("Please find your session vigilance details mentioned below,\n\n");
        buffer.append("Days\\Sessions");
        for (int i =0; i < m_vigilanceChart[0].length; i++) {
            buffer.append("\t");
            buffer.append("S" + (i +1));
        }
        buffer.append("\n");
        for (int i =0; i < m_vigilanceChart.length; i++) {
            buffer.append("Day " + (i + 1));
            //buffer.append("            ");
            buffer.append("\t\t\t");
            for (int j =0; j < m_vigilanceChart [i].length; j++) {
                int value = m_vigilanceChart[i][j];
                buffer.append(value == 0 ? "0" : (m_isSupervisor ? "S" : "1"));
                buffer.append("\t");
            }
            buffer.append("\n");

        }
        return buffer.toString();
    }

    public String toString () {
        StringBuffer buffer = new StringBuffer();
        buffer.append(printVigilanceInfoSubject());
        buffer.append("\n\n");
        buffer.append(printVigilanceInfo());
        return buffer.toString();

    }
}
