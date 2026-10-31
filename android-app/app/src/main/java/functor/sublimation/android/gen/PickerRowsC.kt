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
fun PickerRowsC() {
    androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 40)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(40)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "苯扎托品", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 8h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(40)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 40)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 41)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(41)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "普拉克索", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 8h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(41)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 41)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 42)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(42)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "金刚烷胺", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 15h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(42)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 42)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 43)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(43)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "美金刚", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 70h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(43)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 43)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 44)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(44)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "普瑞巴林", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 6.3h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(44)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 44)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 45)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(45)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "雷美替胺", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 1.6h · F=2% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(45)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 45)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 46)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(46)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "奥氮平", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 30h · F=60% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(46)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 46)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 47)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(47)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "喹硫平", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 7h · F=10% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(47)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 47)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 48)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(48)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "布洛芬", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "非处方药 · T½ 2h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(48)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 48)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 49)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(49)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "塞来昔布", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 11h · F=30% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(49)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 49)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 50)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(50)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "对乙酰氨基酚", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "非处方药 · T½ 2.5h · F=70% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(50)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 50)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 51)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(51)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "咖啡因", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神活性物质 · T½ 5h · F=100% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(51)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 51)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 52)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(52)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "乙醇", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神活性物质 · T½ 4.5h · F=80% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(52)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 52)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 53)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(53)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "槟榔碱", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神活性物质 · T½ 0.5h · F=30% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(53)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 53)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 54)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(54)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "尼古丁", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神活性物质 · T½ 2h · F=40% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(54)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 54)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 55)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(55)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "吸烟（烟草烟雾）", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "精神活性物质 · T½ 2h · F=50% · 吸入·记录", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(55)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 55)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 56)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(56)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "环丙沙星", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 4h · F=70% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(56)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 56)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 57)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(57)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "克拉霉素", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 3.5h · F=55% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(57)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 57)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 58)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(58)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "利福平", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 3.5h · F=90% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(58)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 58)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 59)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(59)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "口服避孕药（炔雌醇/去氧孕烯）", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "处方药 · T½ 17h · F=50% · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(59)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 59)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 60)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(60)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "纳洛酮", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "逆转剂 · 阿片类中毒 · T½ 1h · 鼻喷/IM", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(60)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 60)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 61)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(61)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "氟马西尼", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "逆转剂 · 苯二氮卓类 · T½ 1h · IV", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(61)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 61)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 62)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(62)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "毒扁豆碱", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "逆转剂 · 抗胆碱能危象 · T½ 1h · IV", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(62)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 62)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
        if (functor.sublimation.android.DshComposeHost.boolArg("pickRowVisible", 63)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable { functor.sublimation.android.DshComposeHost.act("pickDrugAt", listOf(63)) }.padding(horizontal = 12.dp, vertical = 10.dp)) {
            androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1.0f)) {
                androidx.compose.material3.Text(text = "赛庚啶", modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                androidx.compose.material3.Text(text = "逆转剂 · 血清素综合征 · T½ 8h · 口服", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.0.sp)
            }
            androidx.compose.foundation.layout.Column(modifier = Modifier.clickable { functor.sublimation.android.DshComposeHost.act("openDocFor", listOf(63)) }.padding(horizontal = 4.dp)) {
                androidx.compose.material3.Text(text = "ⓘ", modifier = Modifier, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            if (functor.sublimation.android.DshComposeHost.boolArg("isSel", 63)) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                androidx.compose.material3.Text(text = "✓", modifier = Modifier, color = MaterialTheme.colorScheme.primary, fontSize = 16.0.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
            }
        }
        }
    }
}
