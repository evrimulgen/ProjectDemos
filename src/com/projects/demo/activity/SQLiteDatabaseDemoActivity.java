package com.projects.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.data.BooksDB;
  
public class SQLiteDatabaseDemoActivity extends Activity implements
        AdapterView.OnItemClickListener { 
    private BooksDB mBooksDB; 
    private Cursor mCursor; 
    private EditText bookName; 
    private EditText bookAuthor; 
    private ListView booksList;
  
    private int BOOK_ID = 0; 
    protected final static int MENU_ADD = Menu.FIRST; 
    protected final static int MENU_DELETE = Menu.FIRST + 1; 
    protected final static int MENU_UPDATE = Menu.FIRST + 2; 
  
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.database_demo_main); 
        setUpViews(); 
    } 
  
    public void setUpViews() { 
        mBooksDB = new BooksDB(this); 
        mCursor = mBooksDB.select(); 
  
        bookName = (EditText) findViewById(R.id.bookname); 
        bookAuthor = (EditText) findViewById(R.id.author); 
        booksList = (ListView) findViewById(R.id.bookslist); 
  
        booksList.setAdapter(new BooksListAdapter(this, mCursor)); 
        booksList.setOnItemClickListener(this); 
    } 
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { 
        super.onCreateOptionsMenu(menu); 
  
        menu.add(Menu.NONE, MENU_ADD, 0, "ADD"); 
        menu.add(Menu.NONE, MENU_DELETE, 0, "DELETE"); 
        menu.add(Menu.NONE, MENU_UPDATE, 0, "UPDATE"); 
        return true; 
    } 
  
    public boolean onOptionsItemSelected(MenuItem item) { 
        super.onOptionsItemSelected(item); 
        switch (item.getItemId()) { 
        case MENU_ADD: 
            add(); 
            break; 
        case MENU_DELETE: 
            delete(); 
            break; 
        case MENU_UPDATE: 
            update(); 
            break; 
        } 
        return true; 
    } 
  
    public void add() { 
        String bookname = bookName.getText().toString(); 
        String author = bookAuthor.getText().toString(); 
        // 书名和作者都不能为空，或者退出 
        if (bookname.equals("") || author.equals("")) { 
            return; 
        } 
        mBooksDB.insert(bookname, author); 
        mCursor.requery(); 
        booksList.invalidateViews(); 
        bookName.setText(""); 
        bookAuthor.setText(""); 
        Toast.makeText(this, "Add Successed!", Toast.LENGTH_SHORT).show(); 
    } 
  
    public void delete() { 
        if (BOOK_ID == 0) { 
            return; 
        } 
        mBooksDB.delete(BOOK_ID); 
        mCursor.requery(); 
        booksList.invalidateViews(); 
        bookName.setText(""); 
        bookAuthor.setText(""); 
        Toast.makeText(this, "Delete Successed!", Toast.LENGTH_SHORT).show(); 
    } 
  
    public void update() { 
        String bookname = bookName.getText().toString(); 
        String author = bookAuthor.getText().toString(); 
        // 书名和作者都不能为空，或者退出 
        if (bookname.equals("") || author.equals("")) { 
            return; 
        } 
        mBooksDB.update(BOOK_ID, bookname, author); 
        mCursor.requery(); 
        booksList.invalidateViews(); 
        bookName.setText(""); 
        bookAuthor.setText(""); 
        Toast.makeText(this, "Update Successed!", Toast.LENGTH_SHORT).show(); 
    } 
  
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, 
            long id) { 
  
        mCursor.moveToPosition(position); 
        BOOK_ID = mCursor.getInt(0); 
        bookName.setText(mCursor.getString(1)); 
        bookAuthor.setText(mCursor.getString(2)); 
  
    } 
  
    public class BooksListAdapter extends BaseAdapter { 
        private Context mContext; 
        private Cursor mCursor; 
  
        public BooksListAdapter(Context context, Cursor cursor) { 
  
            mContext = context; 
            mCursor = cursor; 
        } 
  
        @Override
        public int getCount() { 
            return mCursor.getCount(); 
        } 
  
        @Override
        public Object getItem(int position) { 
            return null; 
        } 
  
        @Override
        public long getItemId(int position) { 
            return 0; 
        } 
  
        @Override
        public View getView(int position, View convertView, ViewGroup parent) { 
            TextView mTextView = new TextView(mContext); 
            mCursor.moveToPosition(position); 
            mTextView.setText(mCursor.getString(1) + "___"
                    + mCursor.getString(2)); 
            return mTextView; 
        } 
  
    } 
} 
