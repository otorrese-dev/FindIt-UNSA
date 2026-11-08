# Instrucciones de ejecución

## Requisitos
- JDK 21 instalado.
- Terminal en la raíz del proyecto.

## Compilar
Linux/macOS:
```bash
javac -d out $(find src/main/java -name "*.java")
```

Windows PowerShell:
```powershell
javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
```

## Ejecutar
```bash
java -cp out encuentraU.Main
```

## Usuarios demo
- Administrador: `admin@encuentrau.edu` / `admin123`
- Usuario: `ana@universidad.edu` / `1234`

## Notas
- Los datos se pierden al cerrar la aplicación.
- No se utiliza base de datos en este Sprint.
