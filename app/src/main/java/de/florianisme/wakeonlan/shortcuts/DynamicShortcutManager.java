package de.florianisme.wakeonlan.shortcuts;

import android.content.Context;
import android.os.Build;

import androidx.core.content.pm.ShortcutManagerCompat;

import java.util.List;

import de.florianisme.wakeonlan.persistence.models.Device;

public class DynamicShortcutManager {

    public void updateShortcuts(Context context, List<Device> devices) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }

        removeOldShortcuts(context);
        publishShortcuts(context, devices);
    }

    private void publishShortcuts(Context context, List<Device> devices) {
        devices.stream()
                .sorted((device1, device2) -> Integer.compare(device2.id, device1.id))
                .map(device -> DeviceShortcutMapper.buildShortcut(device, context))
                .forEach(shortcut -> ShortcutManagerCompat.pushDynamicShortcut(context, shortcut));
    }

    private void removeOldShortcuts(Context context) {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context);
    }

}
