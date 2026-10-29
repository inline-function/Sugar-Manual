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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
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
            androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("openDrugList") }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
            androidx.compose.material3.TextButton(onClick = { functor.sublimation.android.DshComposeHost.act("openDrugList") }) {
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
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(start = 24.dp, top = 10.dp, end = 24.dp, bottom = 8.dp)) {
                        functor.sublimation.android.DshComposeHost.Subl_EffectTags(tags = functor.sublimation.android.DshComposeHost.effectTags(), modifier = Modifier)
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 0)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name0"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose0"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time0"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 1)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name1"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose1"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time1"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 2)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name2"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose2"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time2"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 3)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name3"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose3"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time3"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 4)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name4"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose4"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time4"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 5)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name5"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose5"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time5"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 6)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name6"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose6"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time6"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 7)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name7"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose7"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time7"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 8)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name8"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose8"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time8"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("hasRec", 9)) {
                    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Card(colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), elevation = androidx.compose.material3.CardDefaults.cardElevation(0.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 6.dp)) {
                            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                                androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth()) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("name9"), modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("dose9"), modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("time9"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                            }
                        }
                    }
                    }
                    if (functor.sublimation.android.DshComposeHost.boolArg("noRec")) {
                    androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 12.dp)) {
                        androidx.compose.material3.Text(text = "暂无服药记录（空台账时上方曲线为演示数据）", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.0.sp)
                    }
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
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(0)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(1)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                        androidx.compose.material3.Text(text = "处方药 · T½ 96h · F≈60% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(2)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(3)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(4)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(5)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                        androidx.compose.material3.Text(text = "处方药 · T½ 36h · F≈90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(6)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                        androidx.compose.material3.Text(text = "处方药 · T½ 17h · F≈100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(7)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                        androidx.compose.material3.Text(text = "处方药 · T½ 11h · F≈90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(8)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
                                        androidx.compose.material3.Text(text = "处方药 · T½ 30h · F≈90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(9)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 9)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(10)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "劳拉西泮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 12h · F≈94% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(10)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 10)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(11)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "咪达唑仑", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 3h · F=44% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(11)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 11)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(12)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "吗啡", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 2h · F=34% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(12)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 12)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(13)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "羟考酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 4h · F≈87% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(13)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 13)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(14)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "芬太尼", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 17h · F≈92% · 透皮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(14)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 14)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(15)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "可待因", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 3h · F≈60% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(15)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 15)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(16)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "曲马多", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 6h · F=66% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(16)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 16)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                                        androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    }
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(17)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "哌替啶", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 4h · F≈50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(17)) }.padding(horizontal = 4.dp)) {
                                        androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    }
                                    if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 17)) {
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
                if (functor.sublimation.android.DshComposeHost.boolArg("isListOpen")) {
                androidx.compose.material3.ModalBottomSheet(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("closeDrugList") }, sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)) {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
                        androidx.compose.material3.Text(text = "药库名单", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 22.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        androidx.compose.material3.Text(text = "糖手册：记录服药 → 估算血药浓度 → 呼吸抑制风险预警。点击药名查看该药完整资料。", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().height(320.dp).verticalScroll(rememberScrollState())) {
                            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(0)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "氟伏沙明", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 15h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(1)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "帕罗西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 24h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(2)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "氟西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 96h · F≈60% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(3)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "舍曲林", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 26h · F=44% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(4)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "西酞普兰", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 35h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(5)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "艾司西酞普兰", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 30h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(6)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF1976D2))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "地西泮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 36h · F≈90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(7)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF1976D2))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "艾司唑仑", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 17h · F≈100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(8)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF1976D2))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "阿普唑仑", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 11h · F≈90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(9)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF1976D2))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "氯硝西泮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 30h · F≈90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(10)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF1976D2))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "劳拉西泮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 12h · F≈94% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(11)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF1976D2))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "咪达唑仑", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 3h · F=44% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(12)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF6A1B9A))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "吗啡", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 2h · F=34% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(13)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF6A1B9A))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "羟考酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 4h · F≈87% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(14)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF6A1B9A))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "芬太尼", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 17h · F≈92% · 透皮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(15)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF6A1B9A))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "可待因", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 3h · F≈60% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(16)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF6A1B9A))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "曲马多", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 6h · F=66% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                                androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(17)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFF6A1B9A))) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                        // 空容器
                                    }
                                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                                        androidx.compose.material3.Text(text = "哌替啶", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                        androidx.compose.material3.Text(text = "处方药 · T½ 4h · F≈50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                                    }
                                    androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
                }
                if (functor.sublimation.android.DshComposeHost.boolArg("isDocOpen")) {
                androidx.compose.material3.ModalBottomSheet(onDismissRequest = { functor.sublimation.android.DshComposeHost.act("closeDoc") }, sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)) {
                    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
                        androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("docName"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 22.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().height(480.dp).verticalScroll(rememberScrollState())) {
                            androidx.compose.material3.Text(text = functor.sublimation.android.DshComposeHost.strArg("docBody"), modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                        }
                        androidx.compose.material3.Text(text = "资料来源：糖手册内置药物资料（与 .subl 同源；F≈ 为估算值）", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.0.sp)
                    }
                }
                }
            }
        }
    }
}
