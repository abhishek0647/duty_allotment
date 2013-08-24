package org.da;

import javax.mail.MessagingException;
import java.io.*;
import java.util.Properties;

/**
 * @author Abhishek
 */
public class DAReader {

    public static final String NUM_SESSIONS_IN_A_DAY = "numSessionsInADay";


    private String m_daFileName;
    private int m_numSessions;

    public DAReader(String daFileName, Properties properties) {
        m_daFileName = daFileName;
        String numSessionsString = properties.getProperty(NUM_SESSIONS_IN_A_DAY);
        try {
            if (numSessionsString == null) {
                throw new IllegalArgumentException("The property [" + NUM_SESSIONS_IN_A_DAY + "] is missing.");
            }
            m_numSessions = Integer.parseInt(numSessionsString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The property [" + NUM_SESSIONS_IN_A_DAY + "] is not an integer. Found value [" + numSessionsString + "]");
        }
    }


    public DAModel readDAFile() throws IOException {
        File daFile = new File(m_daFileName);
        if (!daFile.exists()) {
            throw new IOException("The file [" + m_daFileName + "] could not be found.");
        }

        BufferedReader reader = new BufferedReader(new FileReader(daFile));
        String readLine = reader.readLine();//Ignore the first line as it is a header line.
        String[] lineTokens = readLine.split(",");
        int totalSessions = lineTokens.length - 4;
        int numDays = totalSessions / m_numSessions;
        DAModel model = new DAModel(numDays, m_numSessions);
        readLine = reader.readLine();
        while (readLine != null) {
            lineTokens = readLine.split(",");
            DAInfo teacherInfo = new DAInfo(Integer.parseInt(lineTokens[0]), lineTokens[1], lineTokens[2], lineTokens.length < 4 ? null : lineTokens[3], numDays, m_numSessions);
            for (int i =0; i < totalSessions; i++) {
                String value = lineTokens.length > (i + 4) ? lineTokens [i + 4] : null;
                int dayCount = i / m_numSessions;
                int sessionCount = i % m_numSessions;
                if (value != null && !value.isEmpty()) {
                    teacherInfo.markVigilance(dayCount, sessionCount, !value.equals("1"));
                }
            }
            model.addDAInfo(teacherInfo);
            readLine = reader.readLine();
        }
        return model;
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties ();
        props.load(new FileInputStream(args[0]));
        DAReader reader = new DAReader(args [1], props);
        DAModel model = reader.readDAFile();
        for (DAInfo daInfo : model.getTeacherVigilanceInfo()) {
            String email = daInfo.getEmail();
            if (email != null && !email.trim().isEmpty()) {
                System.out.println("Sending Email to [" + daInfo.getTeacherName() +"] from [" + daInfo.getDept() + "] at [" + email +"]");
                try {
                    SendMail.sendBySMVITGMail(email, daInfo.printVigilanceInfoSubject(), daInfo.printVigilanceInfo());
                    System.out.println("Successfully sent email to [" + email +"]");
                } catch (MessagingException e) {
                    System.out.println("Error occurred while sending email to [" + email +"]. Error [" + e.getMessage() +"]");
                    e.printStackTrace();
                }
            } else {
                System.out.println("No email specified. Skipping Sending Email to [" + daInfo.getTeacherName() +"] from [" + daInfo.getDept() + "]");
            }
        }
    }
}
