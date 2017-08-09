package com.foxit.ui;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.foxit.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FileBrowserActivity extends Activity {
    public static final String TAG = "FileBrowserActivity";

    private List<HashMap<String, Object>> listData = null;
    private ListView fileListView = null;
    private boolean isListEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_browser);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fileListView = (ListView) findViewById(R.id.fileListView);
        TextView textView = (TextView) findViewById(R.id.fileName);
        fileListView.requestChildFocus(textView, fileListView);
        fileListView.setSelected(true);

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File exDir = Environment.getExternalStorageDirectory();
            inflateListView(exDir);
        } else {
            Toast.makeText(FileBrowserActivity.this, "Not find sdcard", Toast.LENGTH_SHORT)
                    .show();
        }
        fileListView.setOnItemClickListener(new FileItemOnClick());
    }

    //Populate item list for a directory path
    private void inflateList(File file) {
        // TODO Auto-generated method stub
        listData = new ArrayList<HashMap<String, Object>>();
        File[] listFiles = file.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) return true;
                String suffix = file.getName().toLowerCase();
                if (suffix.endsWith(".pdf")) return true;
//				if (suffix.endsWith(".xps")) return true;
//				if (suffix.endsWith(".cbz")) return true;
//				if (suffix.endsWith(".epub")) return true;
//				if (suffix.endsWith(".fb2")) return true;
                return false;
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("icon", R.drawable.fm_superfolder);
        map.put("filename", "..");
        map.put("path", file.getAbsolutePath());
        listData.add(map);
        if (listFiles != null) {
            for (File f : listFiles) {
                map = new HashMap<String, Object>();
                if (f.isDirectory() && !f.getName().startsWith(".")) {
                    map.put("icon", R.drawable.fm_folder_icon);
                    map.put("filename", f.getName());
                    map.put("path", f.getPath());
                    listData.add(map);
                }
            }
            for (File f : listFiles) {
                map = new HashMap<String, Object>();
                if (f.getName().endsWith(".pdf")) {
                    map.put("icon", R.drawable.fm_default_pdf_icon);
                    map.put("filename", f.getName());
                    map.put("path", f.getPath());
                    listData.add(map);
                }
            }
        }

        if (listData.isEmpty()) {
            isListEmpty = true;
        } else {
            isListEmpty = false;
        }
    }

    public void inflateListView(File file) {

        inflateList(file);
        if (!isListEmpty) {
            fileListView.setAdapter(getListAdapter());
        }
    }

    private ListAdapter getListAdapter() {
        // TODO Auto-generated method stub

        SimpleAdapter adapter = new SimpleAdapter(this, listData,
                R.layout.file_list, new String[]
                {"icon", "filename"}, new int[]
                {R.id.icon, R.id.fileName});
        return adapter;
    }

    public class FileItemOnClick implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            HashMap<String, Object> map = listData.get(position);
            String path = (String) map.get("path");
            File file = new File(path);
            File parentpath = file.getParentFile();

            if (position == 0) {
                if (path.equals(Environment.getExternalStorageDirectory().getPath())) {
                    inflateListView(Environment.getExternalStorageDirectory());
                    Toast.makeText(FileBrowserActivity.this, "Top Directory",
                            Toast.LENGTH_LONG).show();
                } else {
                    inflateListView(parentpath);
                }
            } else {
                if (file.isDirectory()) {
                    inflateListView(new File(path));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("fileDir", path);
                    intent.setClass(FileBrowserActivity.this, FoxitPdfActivity.class);
                    startActivity(intent);
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (listData.isEmpty() || listData == null)
                return false;
            HashMap<String, Object> map = listData.get(0);
            String path = null;
            path = (String) map.get("path");
            File file = null;
            file = new File(path);
            if (file.getParent() != null) {
                File parentpath = file.getParentFile();
                if (path.equals(Environment.getExternalStorageDirectory().getPath())) {
                    this.finish();
                } else {
                    inflateListView(parentpath);
                }
            }
            return false;
        }
        return false;
    }
}
