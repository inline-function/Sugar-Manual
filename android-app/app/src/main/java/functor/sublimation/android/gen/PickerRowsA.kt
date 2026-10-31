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
fun PickerRowsA() {
    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 0)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 1)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 2)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 3)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 4)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 5)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 6)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 7)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 8)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 9)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 10)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 11)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 12)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 13)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 14)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 15)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 16)) {
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
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 17)) {
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
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 18)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(18)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "碳酸锂", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 24h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(18)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 18)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 19)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(19)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "丙戊酸", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 12h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(19)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 19)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
    }
}
