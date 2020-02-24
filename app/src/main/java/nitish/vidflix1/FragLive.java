package xxxxxxxxxxx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragLive extends Fragment {
    String deviceId="FAILED";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView=inflater.inflate(R.layout.frag_live,container,false);

        deviceId = android.provider.Settings.Secure.getString(getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        return  rootView;
    }
}
