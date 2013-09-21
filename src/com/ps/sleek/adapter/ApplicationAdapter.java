package com.ps.sleek.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ps.sleek.manager.ApplicationManager;
import com.ps.sleek.model.Application;
import com.ps.sleek.R;

public class ApplicationAdapter extends ArrayAdapter<Application> implements OnItemClickListener {
    private Rect mOldBounds = new Rect();
	private ArrayList<Application> mApplications;
	private Context activity;

    public ApplicationAdapter(Context context) {
    	super(context, 0, ApplicationManager.getInstance(context).getApplications());
    	this.activity = context;
    	this.mApplications = ApplicationManager.getInstance(context).getApplications();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Application app = mApplications.get(position);

        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.application, parent, false);
        }

        Drawable icon = app.icon;

        int width = 82;//(int) resources.getDimension(android.R.dimen.app_icon_size);
        int height = 82;//(int) resources.getDimension(android.R.dimen.app_icon_size);

        final int iconWidth = icon.getIntrinsicWidth();
        final int iconHeight = icon.getIntrinsicHeight();

        if (icon instanceof PaintDrawable) {
            PaintDrawable painter = (PaintDrawable) icon;
            painter.setIntrinsicWidth(width);
            painter.setIntrinsicHeight(height);
        }

        if (width > 0 && height > 0 && (width < iconWidth || height < iconHeight)) {
            final float ratio = (float) iconWidth / iconHeight;

            if (iconWidth > iconHeight) {
                height = (int) (width / ratio);
            } else if (iconHeight > iconWidth) {
                width = (int) (height * ratio);
            }

            final Bitmap.Config c =
                    icon.getOpacity() != PixelFormat.OPAQUE ?
                        Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            final Bitmap thumb = Bitmap.createBitmap(width, height, c);
            final Canvas canvas = new Canvas(thumb);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, 0));
            // Copy the old bounds to restore them later
            // If we were to do oldBounds = icon.getBounds(),
            // the call to setBounds() that follows would
            // change the same instance and we would lose the
            // old bounds
            mOldBounds.set(icon.getBounds());
            icon.setBounds(0, 0, width, height);
            icon.draw(canvas);
            icon.setBounds(mOldBounds);
            icon = app.icon = new BitmapDrawable(thumb);
        }
            
        final TextView textView = (TextView) convertView.findViewById(R.id.label);
        textView.setText(app.name);
        
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_image);
        imageView.setImageDrawable(icon);

        return convertView;
    }
    
    /**
     * Starts the selected activity/application in the grid view.
     */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		activity.startActivity(mApplications.get(position).intent);
	}
}
