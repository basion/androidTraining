package cn.com.hakim.androidtraining;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public class ContactActivity extends AppCompatActivity {
    TextView contactText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactText = (TextView) findViewById(R.id.tv_contact);
        List<ContactsItemView> data = getContactList();
        StringBuilder build = new StringBuilder();
        for (ContactsItemView contact :data) {
            build.append(contact.name);
        }
        contactText.setText(build.toString());
    }


    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
    /**
     * 联系人姓名
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;
    private List<ContactsItemView> getContactList(){
        List<ContactsItemView> contactsItemViews = new LinkedList<ContactsItemView>();
        ContentResolver resolver = getContentResolver();
        //手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);//手机号
                if (isEmpty(phoneNumber))
                    continue;
                String handlePhone = formatPhone(phoneNumber);
                if (isEmpty(handlePhone)){
                    continue;
                }else if (handlePhone.length() == 7){//7位固话不作处理

                }else if (handlePhone.length() == 8){//8位固话不作处理

                }else if (handlePhone.length() == 12){//12位带区号固话不做处理

                }else if (handlePhone.length() == 11){//11位带区号固话或者手机号不作处理

                }else if (handlePhone.length() == 13){//如果前两位是86，去掉86，否则忽略
                    if (handlePhone.startsWith("86")){
                        handlePhone = handlePhone.substring(handlePhone.length()-2,handlePhone.length());
                    }else {
                        continue;
                    }
                }else {
                    continue;
                }
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);//名称
                ContactsItemView view = new ContactsItemView();
                view.name = contactName;
                view.phone = phoneNumber;
                contactsItemViews.add(view);
            }
            phoneCursor.close();
        }
//        List<ContactsItemView> cardNumList = getSIMContacts();//TODO:暂时忽略sim卡中的联系人
//        contactsItemViews.addAll(cardNumList);
        return contactsItemViews;
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        for (int i=0;i<str.length();i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
    public static String formatPhone(String rawPhone){
        if (isEmpty(rawPhone)){
            return  "";
        }
        String str = rawPhone.replaceAll("\\D","");
        return str;
    }
}
