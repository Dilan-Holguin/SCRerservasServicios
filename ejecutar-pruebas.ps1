param(
    [ValidateSet('criticas', 'ui', 'smoke', 'todas', 'validar', 'base11', 'nuevas', 'pendientes')]
    [string]$Grupo = 'criticas',
    [switch]$SinVentana
)
$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
if (-not $env:JAVA_HOME -or -not (Test-Path -LiteralPath (Join-Path $env:JAVA_HOME 'bin\java.exe'))) {
    $jdkPruebas = Get-ChildItem 'C:\Program Files\Eclipse Adoptium\jdk-21*' -Directory -ErrorAction SilentlyContinue |
        Sort-Object Name -Descending | Select-Object -First 1
    if (-not $jdkPruebas) { throw 'Configura JAVA_HOME con un JDK válido (recomendado: Java 21).' }
    $env:JAVA_HOME = $jdkPruebas.FullName
}
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
$gradleLocal = Join-Path $PSScriptRoot 'gradlew.bat'
if (Test-Path -LiteralPath $gradleLocal) {
    $gradle = $gradleLocal
} else {
    if (-not (Get-Command gradle -ErrorAction SilentlyContinue)) { throw 'Gradle no está disponible en PATH ni existe gradlew.bat.' }
    $gradle = 'gradle'
}
$etiquetas = @{
    criticas = '@ui and @critica and not @pendiente_producto'
    ui = '@ui and not @pendiente_producto'
    smoke = '@ui and @smoke'
    todas = '@ui and not @pendiente_producto'
    validar = '@ui'
    base11 = '@ui and @base11'
    nuevas = '@ui and @ampliacion and not @pendiente_producto'
    pendientes = '@ui and @pendiente_producto'
}
$argumentos = @('--no-daemon', 'test', '--console=plain', "-Dcucumber.filter.tags=$($etiquetas[$Grupo])")
if ($Grupo -eq 'validar') { $argumentos += '-Dcucumber.execution.dry-run=true' }
if ($SinVentana) { $argumentos += '-Dheadless.mode=true' }
& $gradle @argumentos
exit $LASTEXITCODE
