package higo.com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017/8/26.
 */

public class DateformatUtil {
    public static String getCurrentDatetime(){
        Date date=new Date();
        SimpleDateFormat sfd=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sfd.format(date);
    }
}
