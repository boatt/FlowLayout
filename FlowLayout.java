package com.itheima.demo2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public class FlowLayout extends ViewGroup {
    private int horizontalSpacing = 30;
    private int verticalSpacing = 20;
    private String TAG;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ArrayList<Line> lineList = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取控件宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
        Line line = new Line();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(0, 0);
            if (line.getViewList().size() == 0) {
                line.addView(childView);
            } else if (line.getLineWidth() + horizontalSpacing + childView.getMeasuredWidth() > noPaddingWidth) {
                lineList.add(line);
                line = new Line();
                line.addView(childView);
            } else {
                line.addView(childView);
            }
            if (i==getChildCount()-1){
                lineList.add(line);
            }
        }
        int hight = getPaddingTop() + getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            hight += lineList.get(i).getHeight();
        }
        hight += (lineList.size() - 1) * verticalSpacing;
        setMeasuredDimension(width, hight);
        Log.d(TAG, "集合长度onMeasure"+lineList.size());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLift = getPaddingLeft();
        int paddingTop = getPaddingTop();
        Log.d(TAG, "集合长度"+lineList.size());
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            if (i > 0) {
                paddingTop += lineList.get(i-1).getHeight() + verticalSpacing;
            }
            ArrayList<View> viewList = line.getViewList();
            Log.d(TAG, "每行View的个数: "+viewList.size());
           if(viewList.size()==0){
               continue;
           }
            int remainSpacing = getLineRemainSpacing(line);
            int perSpacing = remainSpacing/viewList.size();

            for (int j = 0; j < viewList.size(); j++) {
                View view = viewList.get(j);
                int widthSpec = MeasureSpec.makeMeasureSpec(view.getMeasuredWidth()+perSpacing,MeasureSpec.EXACTLY);
                view.measure(widthSpec,0);
                if (j == 0) {
                    view.layout(paddingLift, paddingTop, paddingLift+view.getMeasuredWidth(), paddingTop+view.getMeasuredHeight());
                }else{
//                    int measuredLeft = getChildAt(j- 1).getMeasuredWidth()+paddingLift;
                    View preView = viewList.get(j - 1);
                    view.layout(preView.getRight()+horizontalSpacing, preView.getTop(), preView.getRight()+horizontalSpacing+view.getMeasuredWidth(), paddingTop+view.getMeasuredHeight());
                }
            }
        }
        lineList.clear();
    }

    private int getLineRemainSpacing(Line line) {
        return getMeasuredWidth()-getPaddingLeft()-getPaddingRight()-line.getLineWidth();
    }

    class Line {
        private ArrayList<View> viewList;//用来存放当前行所有的子View
        private int width;//表示所有子View的宽+水平间距
        private int height;//行的高度

        public Line() {
            viewList = new ArrayList<>();
        }

        public void addView(View view) {
            if (!viewList.contains(view)) {
                viewList.add(view);
            if (viewList.size() == 1) {
                width = view.getMeasuredWidth();
            } else {
                width += horizontalSpacing + view.getMeasuredWidth();
            }
            height = Math.max(height, view.getMeasuredHeight());
            }
        }

        public ArrayList<View> getViewList() {
            return viewList;
        }

        public int getHeight() {
            return height;
        }

        public int getLineWidth() {
            return width;
        }
    }
}
