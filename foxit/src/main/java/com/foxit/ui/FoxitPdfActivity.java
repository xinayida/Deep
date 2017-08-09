package com.foxit.ui;

import java.io.File;

import com.foxit.bean.AnnotInfo;
import com.foxit.bean.PDFContext;
import com.foxit.controller.ViewController;
import com.foxit.gsdk.PDFException;
import com.foxit.gsdk.PDFLibrary;
import com.foxit.gsdk.pdf.PDFDocument;
import com.foxit.gsdk.pdf.annots.Annot;
import com.foxit.popupmenu.MenuItem;
import com.foxit.popupmenu.PopupMenu;
import com.foxit.popupmenu.PopupMenu.OnItemOnClickListener;
import com.foxit.view.PDFPagerAdapter;
import com.foxit.view.PDFView;
import com.foxit.view.PDFViewPager;
import com.foxit.view.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FoxitPdfActivity extends Activity
{
	private static final String TAG = "MainActivity";
	
	private PDFLibrary pdfLibrary = null;
	private ViewController viewController = null;
	
	private static String license_id = "np0yD/twfhRUzRKWC8WGn98b17djtKl2ZhebD4YlMVQebHRh3Y+Tzw==";
	private static String unlockCode = "8f3o18FtvRkJBDfqrVZS6ba4+6mag0plpVDEmnXBqtsEt80eueFv4h2q9LV5b3Co0OJMscLK733loSznVi+JhS0wrpLv6eS2rwfuqCD6HvFTBVdTJHFO+45GNW8wUpVpLdJIXa+oPctkI/8bhcMGgYjb2xu7U18N6na3ESTwfC5LTNzbKQlTRpup8/3jw0WGT/PI2NR4oqjgjGmoAHaFV823CMTpLsAA3ORQEFHLsgxcbyQG4tdjEri03PF7coU6x9Or4LyS10ofasYIsiLAdp6T9HfM+scSjlSv3BGQhQJ84TFF37BQTPxhKAmS1vbCg8M4nrcS4PLZz8uyqI86ArjVE9E2JrC9efds3tkjo4n6ipt+F+E8ogq2n5fCQOY4mMN4wGqfjs9KbemOjqkMZ/kJHe/bnpak85TcOQ0oLF3puYt5zNeq54KNIZg2NEgI59r8zLwZZIwZaZ5wgAbsYwGrsnxnMs1ZlhYf6NQwFzNY3I4gR64PzK2VirpMpFj3x6iaoEKVWqhOtvBLvsKmNSRUOLFzRz+ymAhI/enB3bHQIlSn3ThV0GPLQT3lN+zQTiksOh+NmL9jRAIQ3kVBSQmi02s9ITDBcp5VbPP0F8Khw/hHMeJNZbdPZDrhjtAmdVlJmTYk2UzMbckdkbyuKP4m/mblnLTNombrSOQV5FPlpW/h/Bi4gRm+Om2025UBtlwETu87UYpYD7rg6aGwtMtU6uYECbAZ81tc+4aC5KtsRPA3AWGJKhfEMDC8d6Rdhky+JdLoXdLFzplJE70OIogByrpMINMmHm4n2PMPZn9vMlSjkZ39D614KHarQSZcfl7RCxm3hwRkswJNMMyRHRNixXtPPTjZZVGDRWOB3hKQ+uLHQFjKbuzVDtoxFl05LNKclJ/4hs8qhip0KAXyjVM/+LAsljY1T/c656q09h8qF/7rZ+1GrdScYSnVRfGBEXnwBE1vkSsAdIxlcus2g5dMJofbtzHd/WFqlFqN0cECQTSrvyEc9JJpL7d2tSn+e8Z7J7f99uYHOYa+I4Py0CB79X51hYvlNB9isegXm2IS748hk4OVcArJNjHBezcQGK71AMrwNTVOesnjdVP4GTsOxv6RMtWtzMdVz04P5MxQzLGC+HpFa2ScJOcQrMEzCQpcpDbZU5Z3j9mYJJ2CP2cg2TETZ7r1AiJ4Lna/yTRWUo1/T/1Jwf/ST6vfGi5EY2is40DVnJ+gZJxaDyTE8LS5pYZkWwXpRcuCNjR8SP9VCzepesaH9IfBaBhpm80a532L2vabHsUJtjVqcLwMgb1JcrAP6UTyzNDGEsNKYB0Sg9CuLmOxtXqqudk77sUtSMfAKWqWLyHoECw5ozLhHZTquxSv7WgcaBo6/EPNQLbiEd4ax4W9nbCYlsHlaYItDHunyVmESe0gcySgQNLLXOGfYEXIO55AWvs/Xv/dTnsc+nZG6KM2NM3iTGwDUlWT17smzXvpiHiRa8XlzkfFWCCtZtD4HmpP1RCFegsHK7+8hxHumOzOwO8Xrq7/DlfuLyUPvoKFPGGVRxmqVMMLUkgixBAyDsQX4Xdp2d4pmBSi/6QHME717qQW1mKWf90OxFygIbIheDmNV+x3lRASWgeTGH0VcqjvESZzoSL9zzhZHEyGjAYkEzRJf5LA2E0/K2Xdng1Mb9XJalP2Zu9Qp8ZvIOEKjK3JSoBxW99o9qTtBQjvHwoQGhFH4yqZFX7Jr2v2gn2WemhyU49WWGlNYE2UjtXQsqRdajhbXSxHUg==";
	private int memorySize = 12 * 1024 * 1024;
	private boolean scaleable = true;
	//variables for layout
	private RelativeLayout layout = null;
	private View topBarView = null;
	private View bottomBarView = null;
	private View search_topBarView = null;
	private View search_bottomBarView = null;
	private EditText searchEditText = null;
	private String searchText = null;
	private static boolean isSeacrhing = false;
	private static String lastFileName = null;  //whether is the same file
	private boolean bOpenSuccess = true;
	private TextView pageindexView = null;
	private PDFContext pdfContext = null;
	
	private static final int MODEL_IMPORT = 1;
	private static final int MODEL_EXPORT = 2;
	private static final int MODEL_INSERT = 3;
	private int operationMode = 0;//import or export
	public static String checkLicense = "Invalid license!!! Please check whether the license has related module!!!";
	private boolean bPressSave = false;
	
	//load foxit pdf library
	static{
		System.loadLibrary("fsdk_android");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//initialize PDF library for using Foixt APIs
		pdfLibrary = PDFLibrary.getInstance();
		try
		{
			pdfLibrary.initialize(memorySize, scaleable);
			pdfLibrary.unlock(license_id, unlockCode);
			int type = pdfLibrary.getLicenseType();
	    	if(type == PDFLibrary.LICENSETYPE_EXPIRED || type == PDFLibrary.LICENSETYPE_INVALID)
	    	{
	    		Log.e(TAG, "onCreate: License is invalid or expired!!!");
	    		Toast.makeText(this, "License is invalid or expired!!!", Toast.LENGTH_LONG).show();
				this.finish();
				return;
	    	}
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			Toast.makeText(FoxitPdfActivity.this, generateMsg(e.getLastError(), "Fail to unlock PDF library!!!", true), Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}
		
		viewController = ViewController.create(this);
		
		String filePath = getFilePath();
		if (lastFileName == null) 
		{
			lastFileName = filePath;
		}
		else
		{
			if (!lastFileName.equals(filePath)) 
			{	
				isSeacrhing = false;
				lastFileName = filePath;
			}
		}
		try
		{
			viewController.openDocument(filePath, null);
		}
		catch (PDFException e)
		{
			if (e.getLastError() == PDFException.ERRCODE_PASSWORD)
			{
				PasswordDialog passwordDialog = new PasswordDialog(this, viewController, filePath, R.style.PasswordDialog);
				passwordDialog.setCanceledOnTouchOutside(false);
				passwordDialog.showDialog();
			}
			else {
				Toast.makeText(FoxitPdfActivity.this, generateMsg(e.getLastError(), "Fail to open document!!!", true), Toast.LENGTH_LONG).show();
				bOpenSuccess = false;
				this.finish();			
				return;
			}
			
		}
		if(bOpenSuccess)
		{
			viewController.initialize();
			viewController.setParent(this);
			pdfContext = viewController.getTaskManager().getPDFContext();

			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			requestWindowFeature(Window.FEATURE_NO_TITLE);

			//show view
			layout = new RelativeLayout(this);	
			if (!isSeacrhing)
			{
				setLayout(layout);
			}
			else {
				resetLayoutOnSearch(layout);
			}
		}
	}

	public static String generateMsg(int err, String msg, boolean printErr) { 
		if (err == PDFException.ERRCODE_INVALIDLICENSE)
			return checkLicense;
		else if (printErr)
			return msg + " Error code:" + err;
		else
			return msg;
	} 
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		menu.clear();
		if(pdfContext.isSupportPSI() == true)
			getMenuInflater().inflate(R.menu.psi_menu, menu);
		else {
			getMenuInflater().inflate(R.menu.main, menu);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item)
	{
		if (item.getItemId() == R.id.save)
		{
			//save the current document
			if (viewController != null && lastFileName != null)
			{
				String fileName = lastFileName + ".tmp";
				viewController.saveDocument(fileName, PDFDocument.SAVEFLAG_OBJECTSTREAM);
				bPressSave = true;
			}
		}
		//cancel PSI operation
		if(item.getItemId() == R.id.cancel){
			pdfContext.setPSIFlag(false);
			viewController.cancelPsi();
			resetLayoutAfterPsi(layout);
		}
		//comfirm PSI operation
		if(item.getItemId() == R.id.confirm){
			pdfContext.setPSIFlag(false);
			viewController.confirmPsi();
			resetLayoutAfterPsi(layout);
		}
		
		return true;
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if(viewController!=null)
		{
			viewController.destroy();
			viewController.closePage();			
			viewController.closeDocument();
		}
		viewController = null;
		if(pdfLibrary!=null)
			pdfLibrary.destroy();
		pdfLibrary = null;
		viewController = null;
	}
	
	private String getFilePath() 
	{
		Intent intent = getIntent();
		String filePath = intent.getStringExtra("fileDir");
		
		if(filePath == null || filePath.length() == 0)
		{
			Uri uri = intent.getData();
			filePath = uri.getPath();
		}
		
		return filePath;
	}
	
	//set and show view
	private void setLayout(RelativeLayout layout)
	{
		
		layout.addView(viewController.getViewPager());//viewpager
		
		//top bar
		topBarView = View.inflate(this, R.layout.top_bar, null);
		
		layout.addView(topBarView);
		
		//bottom bar
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		bottomBarView = View.inflate(this, R.layout.bottom_bar, null);
		layout.addView(bottomBarView, layoutParams);	
		
		layout.addView(View.inflate(this, R.layout.pageindex, null));
		
		setContentView(layout);

		//show pageindex
		pageindexView = (TextView) findViewById(R.id.textView1);
		pageindexView.setText((pdfContext.pageIndex + 1) + "/" + pdfContext.pageCount);
		//monitor Bar
		monitorBar(toolbarListener);
	}
	
	private void monitorBar(OnCheckedChangeListener listener)
	{
		//Monitor top bar
		RadioGroup topBarGroup = (RadioGroup) findViewById(R.id.toolbar_top);
		topBarGroup.setOnCheckedChangeListener(listener);
		
		//Monitor bottom bar
		RadioGroup bottomBarGroup = (RadioGroup) findViewById(R.id.toolbar_bottom);
		bottomBarGroup.getBackground().setAlpha(128);
		bottomBarGroup.setOnCheckedChangeListener(listener);
	}
	
	//reset layout
	private void resetLayoutOnSearch(RelativeLayout layout)
	{
		if (isSeacrhing)
		{
			layout.addView(viewController.getViewPager());
		}
		else {//remove top&bottom bar first
			layout.removeViewInLayout(topBarView);
			layout.removeViewInLayout(bottomBarView);
		}
	
		//add search_top&bottom bar to the layout
		search_topBarView = View.inflate(FoxitPdfActivity.this, R.layout.search_top_bar, null);
		layout.addView(search_topBarView);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		search_bottomBarView = View.inflate(FoxitPdfActivity.this, R.layout.search_bottom_bar, null);
		layout.addView(search_bottomBarView, layoutParams);
		
		if (isSeacrhing)
		{	
			layout.addView(View.inflate(this, R.layout.pageindex, null));
			setContentView(layout);
			//show pageindex
			pageindexView = (TextView) findViewById(R.id.textView1);
			pageindexView.setText((pdfContext.pageIndex + 1) + "/" + pdfContext.pageCount);
		}
		
		//monitor search bar
		monitorSearchBar(toolbarListener);
		
		searchEditText = (EditText) findViewById(R.id.searchText);
		searchEditText.setOnKeyListener(keyListener);
		
		isSeacrhing = true;
	}
	
	private void resetLayoutAfterSearch(RelativeLayout layout)
	{
		//remove search top&bottom bar first
		layout.removeViewInLayout(search_topBarView);
		layout.removeViewInLayout(search_bottomBarView);
		
		//add top&bottom bar to the layout
		//top bar
		topBarView = View.inflate(this, R.layout.top_bar, null);
		layout.addView(topBarView);
		
		//bottom bar
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		bottomBarView = View.inflate(this, R.layout.bottom_bar, null);
		layout.addView(bottomBarView, layoutParams);
		
		//monitor Bar
		monitorBar(toolbarListener);
		
		isSeacrhing = false;
	}
	
	private void resetLayoutOnPsi(RelativeLayout layout){
		
		layout.removeViewInLayout(topBarView);
		layout.removeViewInLayout(bottomBarView);
		setContentView(layout);
	
	}
	
	private void resetLayoutAfterPsi(RelativeLayout layout){
		//top bar
		topBarView = View.inflate(this, R.layout.top_bar, null);
		
		layout.addView(topBarView);
		
		//bottom bar
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		bottomBarView = View.inflate(this, R.layout.bottom_bar, null);
		layout.addView(bottomBarView, layoutParams);	
		
		//monitor Bar
		monitorBar(toolbarListener);
	}
	private void monitorSearchBar(OnCheckedChangeListener listener)
	{
		RadioGroup bottomBarGroup = (RadioGroup) findViewById(R.id.search_toolbar_bottom);
		if (bottomBarGroup == null) return;
		bottomBarGroup.getBackground().setAlpha(128);
		bottomBarGroup.setOnCheckedChangeListener(listener);
	}
	
	OnCheckedChangeListener toolbarListener = new OnCheckedChangeListener()	
	{
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			if(viewController == null)
				return;
			RadioButton radioButton = (RadioButton) FoxitPdfActivity.this.findViewById(checkedId);
			if (checkedId == R.id.radio_prevPage) {
				viewController.turnpage(false);

			} else if (checkedId == R.id.radio_search) {
				resetLayoutOnSearch(layout);

			} else if (checkedId == R.id.radio_nextPage) {
				viewController.turnpage(true);

			} else if (checkedId == R.id.radio_zoomIn) {
				viewController.zoom(true);

			} else if (checkedId == R.id.radio_zoomOut) {
				viewController.zoom(false);

			} else if (checkedId == R.id.radio_searchPrev) {
				viewController.searchPrev();

			} else if (checkedId == R.id.radio_searchNext) {
				viewController.searchNext();

			} else if (checkedId == R.id.radio_annot) {
				WindowManager wm = (WindowManager) FoxitPdfActivity.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				PopupMenu menu = new PopupMenu(FoxitPdfActivity.this, display.getWidth() / 5, LayoutParams.WRAP_CONTENT);
				menu.addMenuItem(new MenuItem("Highlight"));
				menu.addMenuItem(new MenuItem("Underline"));
				menu.addMenuItem(new MenuItem("Link"));
				menu.addMenuItem(new MenuItem("FreeText"));
				menu.addMenuItem(new MenuItem("Note"));
				menu.addMenuItem(new MenuItem("PSI"));
				menu.addMenuItem(new MenuItem("Import"));
				menu.addMenuItem(new MenuItem("Export"));
				menu.inflateMenuItems();
				menu.show(radioButton, 3);
				menu.setItemOnClickListener(annotItemOnClickListener);

			} else if (checkedId == R.id.radio_rotate) {
				WindowManager wm = (WindowManager) FoxitPdfActivity.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				PopupMenu menu = new PopupMenu(FoxitPdfActivity.this, display.getWidth() / 4, LayoutParams.WRAP_CONTENT);
				menu.addMenuItem(new MenuItem("Rotate Left"));
				menu.addMenuItem(new MenuItem("Rotate Right"));
				menu.inflateMenuItems();
				menu.show(radioButton, 1);
				menu.setItemOnClickListener(rotationItemOnClickListener);

			} else if (checkedId == R.id.radio_otherfunctions) {
				WindowManager wm = (WindowManager) FoxitPdfActivity.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				PopupMenu menu = new PopupMenu(FoxitPdfActivity.this, display.getWidth() / 4, LayoutParams.WRAP_CONTENT);
				menu.addMenuItem(new MenuItem("InsertImage"));
				menu.inflateMenuItems();
				menu.show(radioButton, 2);
				menu.setItemOnClickListener(otherFunctionsItemOnClickListener);

			} else {
			}
			radioButton.setChecked(false);
			//update pageindex
			if(pageindexView != null)
			    pageindexView.setText((pdfContext.pageIndex + 1) + "/" + pdfContext.pageCount);
		}
	};

	public void onBack(View view)
	{
		resetLayoutAfterSearch(layout);
		//reset searchText
		searchText = null;
		clearHighlightText();
	}
	
	OnKeyListener keyListener = new OnKeyListener()
	{
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event)
		{
			if (KeyEvent.KEYCODE_ENTER == keyCode
			        && event.getAction() == KeyEvent.ACTION_DOWN)
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
				
				clearHighlightText();
				searchText = searchEditText.getText().toString();
				if(!searchText.equals(""))
					viewController.startSearch(FoxitPdfActivity.this, searchText);
				else 
					Toast.makeText(FoxitPdfActivity.this, "Please input search text!", Toast.LENGTH_SHORT).show();
				return true;
			}
			return false;
		}
	};

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		if( viewController== null)
			return;
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){ 
		    viewController.resetConfiguration();
		} 
		else if (this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) { 
			viewController.resetConfiguration(); 
		}
	}
	
	private void clearHighlightText()
	{
		if(viewController == null)
			return;
		PDFViewPager pdfViewPager = (PDFViewPager)viewController.getViewPager();
		PDFPagerAdapter pdfPagerAdapter = (PDFPagerAdapter)pdfViewPager.getAdapter();
		int index = viewController.getTaskManager().getPDFContext().pageIndex;
		PDFView pdfView = pdfPagerAdapter.getViewMap().get(index);
		if (pdfView == null)
			return;
		else
		{
			pdfView.clearHighlight();
			pdfView.postInvalidate();
		}	
		viewController.destroy();
	}
	
	OnItemOnClickListener annotItemOnClickListener = new OnItemOnClickListener()
	{
		
		@Override
		public void onItemClick(MenuItem item, int position)
		{
			String type = null;
			if(pdfContext.getCurrentState().bAnnotLicense == false)
			{
				//Toast or dialog
				Toast.makeText(FoxitPdfActivity.this, "no annot license!!!", Toast.LENGTH_LONG).show();
				return;
			}
			switch (position)
			{
			case 0:
				type = Annot.TYPE_HIGHLIGHT;
				break;
			case 1:
				type = Annot.TYPE_UNDERLINE;
				break;
			case 2:
				type = Annot.TYPE_LINK;
				break;
			case 3:
				type = Annot.TYPE_FREETEXT;				
				break;
			case 4:
				type = Annot.TYPE_TEXT;
				break;
			case 5:
				{	//click to start PSI operation
					if(pdfContext.getCurrentState().rotation == 0){
						if (viewController != null) {
							resetLayoutOnPsi(layout);
							int ret = viewController.initPsi();
							if(ret == PDFException.ERRCODE_INVALIDLICENSE)
							{
								Toast.makeText(FoxitPdfActivity.this, "no psi license!!!", Toast.LENGTH_LONG).show();
								return;
							}
							pdfContext.setPSIFlag(true);
						}
					}
				}
				break;
			case 6:
				{
					Intent intent = new Intent();
					intent.setClass(FoxitPdfActivity.this, FileSelectorActivity.class);
					startActivityForResult(intent, 1);//requestCode = 1
					operationMode = MODEL_IMPORT;
				}
				break;
			case 7:
				{
					Intent intent = new Intent();
					intent.setClass(FoxitPdfActivity.this, FileSelectorActivity.class);
					startActivityForResult(intent, 1);//requestCode = 1
					operationMode = MODEL_EXPORT;
				}
				break;
			
			default:
				break;
			}
			
			if (type != null)
			{
				AnnotInfo annotInfo = new AnnotInfo(type, AnnotInfo.TYPE_ADD);
				//use in ViewContoller
				if(viewController != null)
					viewController.setAnnotInfo(annotInfo);
			}
		}
	};
	
	OnItemOnClickListener otherFunctionsItemOnClickListener = new OnItemOnClickListener()
	{
		
		@Override
		public void onItemClick(MenuItem item, int position)
		{
			switch (position)
			{
			case 0:
			{	//click to insert image
				Intent intent = new Intent();
				intent.setClass(FoxitPdfActivity.this, FileSelectorActivity.class);
				startActivityForResult(intent, 1);//requestCode = 1
				pdfContext.setInsertImgFlag(true);
				operationMode = MODEL_INSERT;
			}
				break;
			default:
				break;
			}			
		}
	};

	OnItemOnClickListener rotationItemOnClickListener = new OnItemOnClickListener()
	{
		
		@Override
		public void onItemClick(MenuItem item, int position)
		{
			int direction = -1;
			switch (position)
			{
			case 0:
				direction = ViewController.ROTATIONDIRECTION_LEFT;
				break;
			case 1:
				direction = ViewController.ROTATIONDIRECTION_RIGHT;
				break;
			default:
				break;
			}			
			if(viewController != null)
				viewController.onRotation(direction);
		}
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		if(viewController == null)
			return;
		if (requestCode == 1 && resultCode == 2)
		{
			Bundle bundle = data.getBundleExtra("Result");
			String filePath = bundle.getString("file");

			if (operationMode == MODEL_IMPORT)
			{					
				//import annot from fdf
				viewController.importAnnotsFromFDF(filePath);
			}
			else if (operationMode == MODEL_EXPORT)
			{
				//export annot to fdf
				viewController.exportAnnotsToFDF(lastFileName, filePath);
			}
			else if (operationMode == MODEL_INSERT) {
				viewController.insertImageToPage(filePath);
			}
			operationMode = 0;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK && viewController != null))
		{
			viewController.destroy();
			viewController.closePage();			
			viewController.closeDocument();
			//release psi
			if (pdfContext.isSupportPSI() == true)
			{
				viewController.releasePsi();
				pdfContext.setPSIFlag(false);
			}
			
			if (bPressSave)
			{
				File file = new File(lastFileName);
				if (file != null)
					file.delete();
				File newfile = new File(lastFileName + ".tmp");
				if (newfile != null)
					newfile.renameTo(file);
				
				bPressSave = false;
			}
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
