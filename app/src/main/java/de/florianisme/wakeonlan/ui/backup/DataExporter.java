package de.florianisme.wakeonlan.ui.backup;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import de.florianisme.wakeonlan.R;
import de.florianisme.wakeonlan.persistence.repository.DeviceRepository;
import de.florianisme.wakeonlan.ui.backup.contracts.ChooseSaveFileDestinationContract;
import de.florianisme.wakeonlan.ui.backup.model.DeviceBackupModel;

public class DataExporter implements ActivityResultCallback<Uri> {

    public static final String FILE_MODE_WRITE = "w";

    private final WeakReference<Context> contextWeakReference;
    private final ActivityResultLauncher<Object> activityResultLauncher;

    public DataExporter(Fragment fragment) {
        this.contextWeakReference = new WeakReference<>(fragment.getContext());
        activityResultLauncher = fragment.registerForActivityResult(new ChooseSaveFileDestinationContract(), this);
    }

    public void exportDevices() {
        activityResultLauncher.launch(null);
    }

    @Override
    public void onActivityResult(Uri uri) {
        if (uri == null) {
            return;
        }

        Context context = contextWeakReference.get();
        try {
            List<DeviceBackupModel> devices = DeviceRepository.getInstance(context).getAll()
                    .stream().map(DeviceBackupModel::new)
                    .collect(Collectors.toList());

            byte[] content = new ObjectMapper().writeValueAsBytes(devices);
            writeDevicesToFile(uri, content, context);

            Toast.makeText(context, context.getString(R.string.backup_message_export_success, devices.size()), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.backup_message_export_error), Toast.LENGTH_SHORT).show();
            Log.e(getClass().getSimpleName(), "Unable to export devices", e);
        }
    }

    private void writeDevicesToFile(Uri uri, byte[] content, Context context) throws Exception {
        ParcelFileDescriptor fileDescriptor = context.getContentResolver().openFileDescriptor(uri, FILE_MODE_WRITE);
        FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());
        fileOutputStream.write(content);

        fileOutputStream.close();
        fileDescriptor.close();
    }

}
