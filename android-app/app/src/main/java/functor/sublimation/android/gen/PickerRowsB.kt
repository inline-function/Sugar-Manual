// 由 Sublimation 编译器 @compose 处理器生成，请勿手改。
@file:Suppress("unused", "unused_variable", "UNUSED_PARAMETER", "UNUSED_VARIABLE", "UNUSED_EXPRESSION", "UNUSED_VALUE")

package functor.sublimation.android.gen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme

@Composable
fun PickerRowsB() {
    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 20)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(20)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "卡马西平", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 12h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(20)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 20)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 21)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(21)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "拉莫三嗪", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 25h · F=98% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(21)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 21)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 22)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(22)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "文拉法辛", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 5h · F=45% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(22)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 22)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 23)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(23)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "度洛西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 12h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(23)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 23)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 24)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(24)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "阿米替林", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 19h · F=45% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(24)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 24)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 25)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(25)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "米氮平", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 30h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(25)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 25)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 26)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(26)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "安非他酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 21h · F=70% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(26)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 26)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 27)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(27)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "曲唑酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 7h · F=65% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(27)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 27)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 28)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(28)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "氯米帕明", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 26h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(28)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 28)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 29)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(29)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "哌甲酯", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神药品（一类） · T½ 3h · F=30% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(29)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 29)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 30)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(30)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "阿托莫西汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 5h · F=70% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(30)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 30)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 31)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(31)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "多奈哌齐", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 70h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(31)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 31)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 32)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(32)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "卡巴拉汀", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 2h · F=40% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(32)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 32)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 33)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(33)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "加兰他敏", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 7h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(33)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 33)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 34)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(34)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "丁螺环酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 2.5h · F=5% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(34)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 34)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 35)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(35)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "苏沃瑞生", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 12h · F=70% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(35)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 35)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 36)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(36)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "莱博瑞生", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 17h · F=65% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(36)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 36)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 37)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(37)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "氯胺酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神药品（一类） · T½ 2.5h · F=20% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(37)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 37)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 38)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(38)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "裸盖菇素", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "研究性物质 · T½ 1h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(38)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 38)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 39)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(39)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "苯海索", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 4h · F=70% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(39)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 39)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
    }
}
