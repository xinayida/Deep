package com.xinayida.deep.pages.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chenenyu.router.annotation.Route;
import com.xinayida.deep.R;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ww on 2017/8/2.
 */

//@Route("filebrowser")
public class FileBrowserActivity extends ListActivity {

    protected final int UPDATE_DELAY = 5000;

    protected File topDirectory, currentDirectory;
    protected ArrayAdapter<Item> adapter;
    protected Timer updateTimer;

    protected static class Item {
        public File file;
        public String string;

        public Item(File file) {
            this.file = file;
            if (file.isDirectory())
                string = file.getName() + "/";
            else
                string = file.getName();
        }

        public Item(File file, String string) {
            this.file = file;
            this.string = string;
        }

        public String toString() {
            return string;
        }
    }

    protected boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;
        return false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		/* Hide 'home' icon on old themes */
//		getActionBar().setDisplayShowHomeEnabled(false);

        topDirectory = Environment.getExternalStorageDirectory(); //getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        currentDirectory = topDirectory;

        adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        TimerTask updateTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateFileList();
                    }
                });
            }
        };
        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(updateTask, 0, UPDATE_DELAY);
    }

    public void onPause() {
        super.onPause();
        updateTimer.cancel();
        updateTimer = null;
    }

    protected void updateFileList() {
        adapter.clear();

        if (!isExternalStorageReadable()) {
            setTitle(R.string.app_name);
            adapter.add(new Item(topDirectory, getString(R.string.library_no_external_storage)));
            return;
        }

        if (!currentDirectory.isDirectory()) {
            setTitle(R.string.app_name);
            adapter.add(new Item(topDirectory, getString(R.string.library_not_a_directory)));
            return;
        }

        String curPath = currentDirectory.getAbsolutePath();
        String topPath = topDirectory.getParentFile().getAbsolutePath();
        if (curPath.startsWith(topPath))
            curPath = curPath.substring(topPath.length() + 1); /* +1 for trailing slash */
        setTitle(curPath + "/");

        File parent = currentDirectory.getParentFile();
        if (parent != null && !currentDirectory.equals(topDirectory))
            adapter.add(new Item(parent, "../"));

        File[] files = currentDirectory.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) return true;
                String suffix = file.getName().toLowerCase();
                if (suffix.endsWith(".pdf")) return true;
                if (suffix.endsWith(".xps")) return true;
                if (suffix.endsWith(".cbz")) return true;
                if (suffix.endsWith(".epub")) return true;
                if (suffix.endsWith(".fb2")) return true;
                return false;
            }
        });

        if (files == null)
            adapter.add(new Item(topDirectory, getString(R.string.library_permission_denied)));
        else
            for (File file : files)
                adapter.add(new Item(file));

        adapter.sort(new Comparator<Item>() {
            public int compare(Item a, Item b) {
                boolean ad = a.file.isDirectory();
                boolean bd = b.file.isDirectory();
                if (ad && !bd) return -1;
                if (bd && !ad) return 1;
                if (a.string.equals("../")) return -1;
                if (b.string.equals("../")) return 1;
                return a.string.compareTo(b.string);
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = adapter.getItem(position);

        if (item.file.isDirectory()) {
            currentDirectory = item.file;
            updateFileList();
            return;
        }

        if (!item.file.isFile())
            return;

//        Intent intent = new Intent(this, PdfDetailActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET); /* launch as a new document */
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.fromFile(item.file));
//        startActivity(intent);
    }
}
