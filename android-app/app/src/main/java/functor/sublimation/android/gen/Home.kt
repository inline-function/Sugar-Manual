// 由 Sublimation 编译器 @compose 处理器生成，请勿手改。
@file:Suppress("unused", "unused_variable", "UNUSED_PARAMETER", "UNUSED_VARIABLE", "UNUSED_EXPRESSION", "UNUSED_VALUE")
@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)

package functor.sublimation.android.gen

import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Info
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme

@Composable
fun Home() {
    androidx.compose.material3.Scaffold(topBar = {
        androidx.compose.material3.TopAppBar(title = {
            androidx.compose.foundation.layout.Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(text = "语法糖手册", fontSize = 22.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(start = 6.dp))
            androidx.compose.material3.Text(text = "V1.0", fontSize = 11.0.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
        }, actions = {
            androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("openDocDialog") }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
            androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("noNavigation") }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Menu, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
            androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("openSettingsDialog") }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Settings, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
        }, colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, actionIconContentColor = MaterialTheme.colorScheme.onPrimary))
    }, floatingActionButton = {
        androidx.compose.material3.ExtendedFloatingActionButton(onClick = { functor.sublimation.android.DshComposeHost.act("toggleForm") }, containerColor = MaterialTheme.colorScheme.primary, icon = { androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Add, contentDescription = "Add") }, text = { androidx.compose.material3.Text(text = "＋服用药物") })
    }) { __sublPadding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize().padding(__sublPadding)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize()) {
                Subl_ConcentrationChart(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.45f), labels = functor.sublimation.android.DshComposeHost.chartLabels(), hours = 48f, series = functor.sublimation.android.DshComposeHost.chartSeries(), mainPoints = functor.sublimation.android.DshComposeHost.chartTotal(), mainColor = MaterialTheme.colorScheme.primary, showLegend = true, badge = "估算")
                androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().weight(1.0f).verticalScroll(rememberScrollState())) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
                        androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            androidx.compose.material3.Text(text = "联用预警", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            androidx.compose.foundation.layout.FlowRow(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                androidx.compose.foundation.layout.Column(modifier = Modifier.clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(Color(0x33D32F2F)).padding(horizontal = 12.dp, vertical = 5.dp)) {
                                    androidx.compose.material3.Text(text = "恶心", modifier = Modifier, color = Color(0xFFC62828), fontSize = 14.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(Color(0x33F9A825)).padding(horizontal = 12.dp, vertical = 5.dp)) {
                                    androidx.compose.material3.Text(text = "失眠", modifier = Modifier, color = Color(0xFFE65100), fontSize = 14.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(Color(0x33F9A825)).padding(horizontal = 12.dp, vertical = 5.dp)) {
                                    androidx.compose.material3.Text(text = "嗜睡", modifier = Modifier, color = Color(0xFFE65100), fontSize = 14.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(Color(0x3355595F)).padding(horizontal = 12.dp, vertical = 5.dp)) {
                                    androidx.compose.material3.Text(text = "头晕", modifier = Modifier, color = Color(0xFF44464F), fontSize = 14.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                            }
                        }
                    }
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 8.dp)) {
                        androidx.compose.material3.Text(text = "服药记录", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                    androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                            androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                    // 空容器
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("doseDrug"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("doseAmount"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            }
                            androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                // 空容器
                            }
                            androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("doseTime"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                        }
                    }
                    androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 12.dp)) {
                        androidx.compose.material3.Text(text = "演示数据：空台账时按示范剂量展示，记录服药后由逻辑层驱动", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                    }
                    androidx.compose.foundation.layout.Column(modifier = Modifier.height(96.dp)) {
                        // 空容器
                    }
                }
                if (functor.sublimation.android.DshComposeHost.boolArg("isFormOpen")) {
                androidx.compose.material3.ModalBottomSheet(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("cancelForm") }, sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)) {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
                        androidx.compose.material3.Text(text = "＋服用药物", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 22.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        androidx.compose.material3.Text(text = "选择药物并填写剂量（mg）", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                        androidx.compose.material3.Text(text = "药物", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.0.sp)
                        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().height(240.dp).verticalScroll(rememberScrollState())) {
                            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(0)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "氟伏沙明", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 15h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 0)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(1)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "帕罗西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 24h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 1)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(2)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "氟西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 96h · F=60% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 2)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(3)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "舍曲林", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 26h · F=44% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 3)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(4)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "西酞普兰", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 35h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 4)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(5)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "艾司西酞普兰", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 30h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 5)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(6)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "地西泮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 36h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 6)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(7)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "艾司唑仑", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 17h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 7)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(8)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "阿普唑仑", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 11h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 8)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(9)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "氯硝西泮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 30h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 9)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                            }
                        }
                        androidx.compose.material3.TextField(value = functor.sublimation.android.DshComposeHost.strArg("curDose"), onValueChange = { v -> functor.sublimation.android.DshComposeHost.actText("setDose", v) }, label = { androidx.compose.material3.Text(text = "剂量 (mg)") }, keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number), singleLine = true, isError = functor.sublimation.android.DshComposeHost.strArg("doseErr").isNotEmpty(), supportingText = { if (functor.sublimation.android.DshComposeHost.strArg("doseErr").isNotEmpty()) androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("doseErr")) })
                        androidx.compose.foundation.layout.Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("cancelForm") }) {
                                androidx.compose.material3.Text(text = "取消")
                            }
                            androidx.compose.material3.Button(onClick = { functor.sublimation.android.DshComposeHost.act("commitDose") }) {
                                androidx.compose.material3.Text(text = "确认")
                            }
                        }
                    }
                }
                }
                if (functor.sublimation.android.DshComposeHost.boolArg("isSettingsOpen")) {
                androidx.compose.material3.AlertDialog(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("closeSettings") }, confirmButton = { androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("clearAll") }) { androidx.compose.material3.Text(text = "清空记录") } }, dismissButton = { androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("closeSettings") }) { androidx.compose.material3.Text(text = "关闭") } }, title = { androidx.compose.material3.Text(text = "设置") }, text = {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Text(text = "语法糖手册 V1.0", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        androidx.compose.material3.Text(text = "SfA（Sublimation for Android）@compose 通路演示\n按钮交互经 DshComposeHost.act 路由到 Kotlin 侧交互态。", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                    }
                    }
                )
                }
                if (functor.sublimation.android.DshComposeHost.boolArg("isDocOpen")) {
                androidx.compose.material3.AlertDialog(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("closeDoc") }, confirmButton = { androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("closeDoc") }) { androidx.compose.material3.Text(text = "知道了") } }, dismissButton = { androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("closeDoc") }) { androidx.compose.material3.Text(text = "关闭") } }, title = { androidx.compose.material3.Text(text = "药物说明") }, text = {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Text(text = "氟伏沙明 / 帕罗西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        androidx.compose.material3.Text(text = "演示子集：SSRI 联用，CYP2D6 抑制-底物相互作用；T½ 与 F 按演示资料。", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                    }
                    }
                )
                }
            }
        }
    }
}
