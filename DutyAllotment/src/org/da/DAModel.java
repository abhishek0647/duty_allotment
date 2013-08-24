package org.da;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhshek
 */
public class DAModel {

    private int m_numberOfDays;
    private int m_numberOfSessions;
    private List <DAInfo> m_teacherVigilanceInfo;

    public DAModel(int numberOfDays, int numberOfSessions) {
        m_numberOfDays = numberOfDays;
        m_numberOfSessions = numberOfSessions;
        m_teacherVigilanceInfo = new ArrayList<DAInfo>();
    }

    public void addDAInfo (DAInfo teacherInfo) {
        m_teacherVigilanceInfo.add(teacherInfo);
    }

    public List<DAInfo> getTeacherVigilanceInfo() {
        return m_teacherVigilanceInfo;
    }


    public String toString () {
        StringBuffer buffer = new StringBuffer();
        for (DAInfo daInfo : m_teacherVigilanceInfo) {
            buffer.append(daInfo);
            buffer.append("\n\n\n\n");
        }
        return buffer.toString();
    }

}
