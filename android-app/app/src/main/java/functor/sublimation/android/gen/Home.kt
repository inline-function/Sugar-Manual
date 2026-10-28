// 由 Sublimation 编译器 @compose 处理器生成，请勿手改。
@file:Suppress("unused", "unused_variable", "UNUSED_PARAMETER", "UNUSED_VARIABLE", "UNUSED_EXPRESSION", "UNUSED_VALUE")
@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)

package functor.sublimation.android.gen

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
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
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme

@Composable
fun Home() {
    androidx.compose.material3.Scaffold(topBar = {
        androidx.compose.material3.TopAppBar(modifier = Modifier.height(48.dp), title = {
            androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(text = "语法糖手册", fontSize = 22.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(start = 6.dp))
            androidx.compose.material3.Text(text = "V1.0", fontSize = 11.0.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
        }, actions = {
            androidx.compose.material3.TextButton(onClick = { }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
            androidx.compose.material3.TextButton(onClick = { }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Menu, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
            androidx.compose.material3.TextButton(onClick = { }) {
                androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Settings, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
        }, colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, actionIconContentColor = MaterialTheme.colorScheme.onPrimary))
    }, floatingActionButton = {
        androidx.compose.material3.ExtendedFloatingActionButton(onClick = { functor.sublimation.android.DshComposeHost.callJs("null", "[]") }, containerColor = MaterialTheme.colorScheme.primary, icon = { androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Add, contentDescription = "Add") }, text = { androidx.compose.material3.Text(text = "＋服用药物") })
    }) { __sublPadding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize().padding(__sublPadding)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize()) {
                Subl_ConcentrationChart(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.45f), labels = listOf("0", "12", "24", "36", "48"), hours = 48f, series = listOf(SublSeries("氟伏沙明", Color(0xFFFFC107), listOf(50.0f, 41.6f, 34.5f, 28.7f, 23.9f, 19.8f, 66.5f, 55.3f, 46.0f, 38.2f, 31.7f, 26.4f, 22.0f))), mainPoints = listOf(50.0f, 41.6f, 34.5f, 28.7f, 23.9f, 19.8f, 66.5f, 55.3f, 46.0f, 38.2f, 31.7f, 26.4f, 22.0f), mainColor = MaterialTheme.colorScheme.primary, showLegend = true, badge = "估算")
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
                            androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                androidx.compose.foundation.layout.Column(modifier = Modifier.width(4.dp).height(20.dp).background(Color(0xFFFFC107))) {
                                    // 空容器
                                }
                                androidx.compose.foundation.layout.Column(modifier = Modifier.width(10.dp)) {
                                    // 空容器
                                }
                                androidx.compose.material3.Text(text = "氟伏沙明", modifier = Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                androidx.compose.material3.Text(text = "100 mg", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            }
                            androidx.compose.foundation.layout.Column(modifier = Modifier.height(4.dp)) {
                                // 空容器
                            }
                            androidx.compose.material3.Text(text = "给药时间 今日 08:00 · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                        }
                    }
                    androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(vertical = 12.dp)) {
                        androidx.compose.material3.Text(text = "演示数据：空台账时按示范剂量展示，记录服药后由逻辑层驱动", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
                    }
                    androidx.compose.foundation.layout.Column(modifier = Modifier.height(96.dp)) {
                        // 空容器
                    }
                }
                // 隐藏（visible = false，数据驱动显隐）
            }
        }
    }
}
