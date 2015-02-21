# Talker - Conversador
==============

Aplicación para teléfonos y tablets con Android OS, orientada a personas con Afasia o desórdenes del habla, para facilitar la comunicación a través de distintas opciones visuales en una pizarra.


## Desarrollo

1 - [Descargar Eclipse ADT (Android Development Tools)](http://developer.android.com/sdk/index.html)
  
2 - Platform Tools - ADB

	1. a . Chequear que ADB no esté ya instalado
	
	1. b . Para tenerlo siempre disponible, agregarlo al path (/usr/bin)
	
		Si queremos tener la misma version que el SDK Android -> Desinstalar el del SO e instalar la nueva

		Comandos:
		
			sudo apt-get remove android-tools-adb
		
			sudo ln -s /home/estefi/carpeta android/sdk/platform-tools/adb /usr/bin
	
	2. Poner el ADB como "demonio":
		
    		Comandos / Configuración:
		
			sudo gedit /etc/rc.local
		
			Agregar antes del "exit 0": "adb &"

	3. Comandos utiles de adb:
		
		adb shell: te loguea en una consola interna del celular

		adb logcat: muestra el log de lo que está pasando en el celular
		
		adb install archivo.apk : instala la aplicación
		
		adb uninstall nombreDelPaquete: remueve la aplicación

3 - Bajar el Target

	Clickear en Android SDK Manager (ícono de Android con flechita)
	
	Install packages
	

4 - Chequear que esté configurada la SDK (deberia estar)

	En eclipse: Windows -> Preferences -> Android
	

5 - Correr la app

	a. Run Android Application 
	
		Si tengo el teléfono celular conectado, me va a preguntar dónde quiero correrlo -emulador o celular- 
		
		a.1 Si elijo el emulador: 
		
			- Launch a new Android Virtual Device
			- Manager
			- Create
				- AVD Nombre (cualquiera)
				- Device (cualquiera)
				- Target (esto sirve para probar otra version de android - hay que bajar la SDK)
				- completar demás campos.
				 
		a.2 Si elijo el teléfono celular

			- Ya se instala la aplicación en el celular y se puede probar desde el mismo equipo. Verificar que el equipo permita correr en modo debug.
			- 
			
## Agradecimientos

Se ha utilizado el [NewQuickAction](https://github.com/lorensiuswlt/NewQuickAction) de [lorensiuswlt](https://github.com/lorensiuswlt)
