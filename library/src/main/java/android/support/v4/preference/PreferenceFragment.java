package android.support.v4.preference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ListView;

import me.shkschneider.skeleton.R;

// https://github.com/kolavar/android-support-v4-preferencefragment
public abstract class PreferenceFragment extends Fragment implements PreferenceManagerCompat.OnPreferenceTreeClickListener {
    
	private static final String PREFERENCES_TAG = "android:preferences";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final int MSG_BIND_PREFERENCES = 1;

    private PreferenceManager mPreferenceManager;
    private ListView mList;
    private boolean mHavePrefs;
    private boolean mInitDone;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_BIND_PREFERENCES:
                    bindPreferences();
                    break;
            }
        }
    };
    final private Runnable mRequestFocus = new Runnable() {
        @Override
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };
    private OnKeyListener mListOnKeyListener = new OnKeyListener() {

        @Override
        public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
            final Object selectedItem = mList.getSelectedItem();
            if (selectedItem instanceof Preference) {
                // return ((Preference) selectedItem).onKey(mList.getSelectedView(), keyCode, event);
                return false;
            }
            return false;
        }

    };
	
    @Override
	public void onCreate(final Bundle paramBundle) {
		super.onCreate(paramBundle);
		mPreferenceManager = PreferenceManagerCompat.newInstance(getActivity(), FIRST_REQUEST_CODE);
		PreferenceManagerCompat.setFragment(mPreferenceManager, this);
	}
	
    @Override
	public View onCreateView(final LayoutInflater paramLayoutInflater, final ViewGroup paramViewGroup, final Bundle paramBundle) {
		return paramLayoutInflater.inflate(R.layout.preference_list_fragment, paramViewGroup, false);
	}
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mHavePrefs) {
            bindPreferences();
        }
        mInitDone = true;
        if (savedInstanceState != null) {
            Bundle container = savedInstanceState.getBundle(PREFERENCES_TAG);
            if (container != null) {
                final PreferenceScreen preferenceScreen = getPreferenceScreen();
                if (preferenceScreen != null) {
                    preferenceScreen.restoreHierarchyState(container);
                }
            }
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        PreferenceManagerCompat.setOnPreferenceTreeClickListener(mPreferenceManager, this);
    }
    
    @Override
	public void onStop() {
		super.onStop();
		PreferenceManagerCompat.dispatchActivityStop(mPreferenceManager);
		PreferenceManagerCompat.setOnPreferenceTreeClickListener(mPreferenceManager, null);
	}
    
    @Override
	public void onDestroyView() {
		mList = null;
		mHandler.removeCallbacks(mRequestFocus);
		mHandler.removeMessages(MSG_BIND_PREFERENCES);
		super.onDestroyView();
	}
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		PreferenceManagerCompat.dispatchActivityDestroy(mPreferenceManager);
	}
    
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        final PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            final Bundle container = new Bundle();
            preferenceScreen.saveHierarchyState(container);
            outState.putBundle(PREFERENCES_TAG, container);
        }
    }
    
    @Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        PreferenceManagerCompat.dispatchActivityResult(mPreferenceManager, requestCode, resultCode, data);
	}

    public PreferenceManager getPreferenceManager() {
        return mPreferenceManager;
    }

    public void setPreferenceScreen(final PreferenceScreen preferenceScreen) {
        if (PreferenceManagerCompat.setPreferences(mPreferenceManager, preferenceScreen) && preferenceScreen != null) {
            mHavePrefs = true;
            if (mInitDone) {
                postBindPreferences();
            }
        }
    }

    public PreferenceScreen getPreferenceScreen() {
        return PreferenceManagerCompat.getPreferenceScreen(mPreferenceManager);
    }

    public void addPreferencesFromIntent(final Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(PreferenceManagerCompat.inflateFromIntent(mPreferenceManager, intent, getPreferenceScreen()));
    }

    public void addPreferencesFromResource(final int preferencesResId) {
        requirePreferenceManager();
        setPreferenceScreen(PreferenceManagerCompat.inflateFromResource(mPreferenceManager, getActivity(), preferencesResId, getPreferenceScreen()));
    }

    public boolean onPreferenceTreeClick(final PreferenceScreen preferenceScreen, final Preference preference) {
        // if (preference.getFragment() != null &&
    	if (getActivity() instanceof OnPreferenceStartFragmentCallback) {
            return ((OnPreferenceStartFragmentCallback) getActivity()).onPreferenceStartFragment(this, preference);
        }
        return false;
    }

    public Preference findPreference(final CharSequence key) {
        if (mPreferenceManager == null) {
            return null;
        }
        return mPreferenceManager.findPreference(key);
    }
    
    private void requirePreferenceManager() {
        if (mPreferenceManager == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
    }
	
    private void postBindPreferences() {
        if (mHandler.hasMessages(MSG_BIND_PREFERENCES)) {
            return ;
        }
        mHandler.obtainMessage(MSG_BIND_PREFERENCES).sendToTarget();
    }
    
    private void bindPreferences() {
        final PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(getListView());
        }
    }

    public ListView getListView() {
        ensureList();
        return mList;
    }

    private void ensureList() {
        if (mList != null) {
            return ;
        }
        final View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        final View rawListView = root.findViewById(android.R.id.list);
        if (rawListView == null) {
            throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
        }
        if (! (rawListView instanceof ListView)) {
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
        }
        mList = (ListView) rawListView;
        mList.setOnKeyListener(mListOnKeyListener);
        mHandler.post(mRequestFocus);
    }

    public interface OnPreferenceStartFragmentCallback {

        boolean onPreferenceStartFragment(final PreferenceFragment caller, final Preference pref);

    }

}