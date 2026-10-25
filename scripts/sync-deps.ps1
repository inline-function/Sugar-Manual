# scripts/sync-deps.ps1 —— Sugar-Manual 仓依赖同步
#
# 用主仓 F:\KotlinProject\Sublimation 的真源同步本仓两处依赖：
#   core/            -> 根 core/（工作区依赖，git ignore 不入库）
#                      与 app/core/（入库快照：编译模块输入的一部分）
#   annotation processer/ -> 根（.subs 处理器，入库）
# 主仓自动探测（-MainDir 覆盖）。
#
# 约定（与 SfA 仓保持一致）：编译时以仓根为 CWD，处理器与 stdl 均按 CWD 相对路径加载；
# sdk-lite 不在本仓范围（APK 打包链由 UI 任务/发布流程另行接入）。

param(
    [string]$MainDir,
    [switch]$DryRun
)

$ErrorActionPreference = "Stop"

function Resolve-MainRepo([string]$Hint) {
    if ($Hint) { return $Hint }
    return "F:\KotlinProject\Sublimation"
}

function Sync-Dir([string]$From, [string]$To, [string]$Tag) {
    if (-not (Test-Path $From)) { Write-Warning "源不存在，跳过 $Tag : $From"; return }
    if ($DryRun) { Write-Host "[dry] $Tag : $From -> $To"; return }
    robocopy $From $To /E /NFL /NDL /NJH /NJS /NP | Out-Null
    Write-Host "已同步 $Tag -> $To"
}

$main = Resolve-MainRepo $MainDir
$core = Join-Path $main "core"
$procs = Join-Path $main "annotation processer"
if (-not (Test-Path $core)) { throw "主仓 core 不存在：$core（-MainDir 指定主仓位置）" }

$repo = Split-Path -Parent $PSScriptRoot
Write-Host "Sugar-Manual 仓：$repo"
Write-Host "主仓：$main"
Sync-Dir $core (Join-Path $repo "core") "core（工作区依赖，git ignore）"
Sync-Dir $core (Join-Path $repo "app\core") "app/core（入库快照）"
Sync-Dir $procs (Join-Path $repo "annotation processer") "annotation processer（入库）"
if (-not $DryRun) { Write-Host "完成。不要提交根 core/（.gitignore 已排除）。" }
