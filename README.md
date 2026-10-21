# MoveFirst

MoveFirst es un prototipo Android personal que invita a hacer cinco minutos de movimiento antes de pasar tiempo en redes sociales.

## Incluye
- Pantalla inicial en español.
- Temporizador de cinco minutos con pausa y reinicio.
- Confirmación de sesión completada.
- Contador local de sesiones guardado en el teléfono.

## Crear el APK
Cada cambio en `main` inicia una compilación en GitHub Actions. También puedes iniciarla manualmente desde la pestaña **Actions** y el flujo **Build MoveFirst APK**.

Cuando termine correctamente:
1. Abre la ejecución más reciente del flujo.
2. Baja hasta **Artifacts**.
3. Descarga `MoveFirst-debug-apk` y extrae el ZIP.
4. En tu teléfono Android, abre `app-debug.apk` y sigue las indicaciones para instalarlo.

Este APK es una versión de prueba (debug). Android puede pedir autorización para instalar apps desde el navegador o gestor de archivos.

## Importante
Esta primera versión **no bloquea técnicamente Instagram, TikTok ni otras aplicaciones**. Por ahora funciona como un recordatorio y registro de movimiento. El bloqueo real de otras apps requiere permisos especiales de Android y una implementación adicional.
