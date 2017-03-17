package com.graph.atfrinds;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView mSpanLinkTest;
    ContactCloudEditTextImpl et_user_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpanLinkTest = (TextView) findViewById(R.id.spanLinkTest);
        et_user_contact = (ContactCloudEditTextImpl) findViewById(R.id.et_user_contact);
        String content = "This is a String";
        SpannableString spannableString = new SpannableString(content);
        ClickableSpan clickableSpan = new CustomizedClickableSpan(content);
        spannableString.setSpan(clickableSpan, 0 , content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mSpanLinkTest.setText("CCCC");
        mSpanLinkTest.append(spannableString);
        mSpanLinkTest.append("BBBB");
        mSpanLinkTest.setMovementMethod(new CustomLinkMovementMethod());
        et_user_contact.addSpan("13588","XXXHHH");//添加块
    }

    class CustomizedClickableSpan extends ClickableSpan {

        String text;

        public CustomizedClickableSpan(String text) {
            super();
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//	        ds.setColor(ds.linkColor);
            ds.setColor(Color.GREEN);
//	        ds.setUnderlineText(false);
            ds.setAlpha(50);
        }

        @Override
        public void onClick(View widget) {
            Spannable spannable = ((Spannable)((TextView)widget).getText());
            Selection.removeSelection(spannable);
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    class CustomLinkMovementMethod extends LinkMovementMethod{
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer,
                                    MotionEvent event) {
            // TODO Auto-generated method stub
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                                buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        buffer.setSpan(new BackgroundColorSpan(Color.GRAY),
                                buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    }

                    return true;
                } else {
                    Selection.removeSelection(buffer);
                }
            }

            return Touch.onTouchEvent(widget, buffer, event);
        }
    }
}
