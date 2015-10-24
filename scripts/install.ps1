$HomeDir = $env:USERPROFILE
$DotfilesDir = $PsScriptRoot
$DotfilesConfig = Join-Path -Path $DotfilesDir -ChildPath "config-win.edn"

#$LatestVersionUrl = "https://clojars.org/api/artifacts/org.ajoberstar/clj-dotfiles"
#$LatestVersion = Invoke-RestMethod -Uri $LatestVersionUrl -ContentType "application/json" | Select -ExpandProperty latest_release

#Write-Host "Downloading latest version: $LatestVersion"

#$JarUrl = "https://clojars.org/repo/org/ajoberstar/clj-dotfiles/$LatestVersion/clj-dotfiles-$LatestVersion.jar"
$JarUrl = "https://clojars.org/repo/org/ajoberstar/clj-dotfiles/0.1.0-SNAPSHOT/clj-dotfiles-0.1.0-20151024.210532-1.jar"
$JarFile = Join-Path -Path $DotfilesDir -ChildPath "clj-dotfiles.jar"

Invoke-WebRequest -Uri $JarUrl -OutFile $JarFile

Write-Host "Executing install:"

java -jar $JarFile $HomeDir $DotfilesDir $DotfilesConfig
