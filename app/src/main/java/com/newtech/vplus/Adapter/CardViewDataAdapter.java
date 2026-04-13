package com.newtech.vplus.Adapter;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.newtech.vplus.R;


public class CardViewDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private Activity activity;
	private HashMap<String,String> url_maps;

	public CardViewDataAdapter(Activity activity,HashMap<String,String> url_maps) {
		this.activity=activity;
		this.url_maps=url_maps;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
		if(viewType == TYPE_HEADER)
        {
			 View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainslider, parent, false);
	         return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainslidersub1, parent, false);
            return new VHItem(v);
        }
		throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		final int fix=position;
		if(viewHolder instanceof VHHeader)
		{
			VHHeader VHheader = (VHHeader)viewHolder;
			for(String name : url_maps.keySet()){
				TextSliderView textSliderView = new TextSliderView(this.activity);
				textSliderView
				.description(name)
				.image(url_maps.get(name))
				.setScaleType(BaseSliderView.ScaleType.Fit)
				.setOnSliderClickListener(this);
				textSliderView.bundle(new Bundle());
				textSliderView.getBundle()
				.putString("extra",name);				
				VHheader.mDemoSlider.addSlider(textSliderView);
			}
			VHheader.mDemoSlider.setPresetTransformer(SliderLayout.Transformer.CubeIn);
			VHheader.mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
			VHheader.mDemoSlider.setCustomAnimation(new DescriptionAnimation());
			VHheader.mDemoSlider.setDuration(4000);
			VHheader.mDemoSlider.addOnPageChangeListener(this);
		}else{
			
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(position==0)
		{return TYPE_HEADER;}
		else
			return TYPE_ITEM;
	}

	// region clickevent
	@Override
	public void onSliderClick(BaseSliderView slider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	// endregion clickevent

	class  VHHeader extends RecyclerView.ViewHolder{
		private SliderLayout mDemoSlider;
		public VHHeader(View itemView) {
			super(itemView);
			mDemoSlider = (SliderLayout) itemView.findViewById(R.id.slider);
		}
	}

	class VHItem  extends RecyclerView.ViewHolder {
		private LinearLayout lv;
		public VHItem (View itemLayoutView) {
			super(itemLayoutView);
			lv=(LinearLayout) itemLayoutView.findViewById(R.id.k1);
		}
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 2;
	}


}


