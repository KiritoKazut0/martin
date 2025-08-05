package com.example.tareas.src.features.tasks.presentation.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import com.example.tareas.src.core.di.HardwareModule
import com.example.tareas.src.features.tasks.di.AppModule
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.presentation.viewModel.DeleteTaskViewModel
import com.example.tareas.src.features.tasks.presentation.viewModel.ListTaskViewModel
import com.example.tareas.src.features.tasks.presentation.viewModel.factory.DeleteTaskViewModelFactory
import com.example.tareas.src.features.tasks.presentation.viewModel.factory.ListTaskViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToUpdate: (String) -> Unit
) {
    val context = LocalContext.current
    val shakeDetector = remember { HardwareModule.provideShakeDetector(context) }

    DisposableEffect(shakeDetector) {
        shakeDetector.startListening {
            onNavigateToCreate()
        }
        onDispose {
            shakeDetector.stopListening()
        }
    }

    val listTaskViewModel: ListTaskViewModel = viewModel(
        factory = ListTaskViewModelFactory(
            AppModule.listTaskUseCase,
            AppModule.userStorageRepository
        )
    )

    val deleteTaskViewModel: DeleteTaskViewModel = viewModel(
        factory = DeleteTaskViewModelFactory(
            AppModule.deleteTaskUseCase
        )
    )

    // Estados del ViewModel
    val tasks by listTaskViewModel.tasks.collectAsState()
    val errorMessage by listTaskViewModel.errorMessage.collectAsState()
    val isLoading by listTaskViewModel.isLoading.collectAsState()
    val deleteStatusMessage by deleteTaskViewModel.deleteStatusMessage.collectAsState()

    // Variable para controlar el dialog de confirmación
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Efecto para recargar las tareas después de eliminar
    LaunchedEffect(deleteStatusMessage) {
        if (deleteStatusMessage == "Task deleted successfully") {
            listTaskViewModel.fetchTasksForUser()
        }
    }

    // Efecto para recarga automática cada 30 segundos
    LaunchedEffect(Unit) {
        listTaskViewModel.fetchTasksForUser() // Carga inicial
        while (true) {
            delay(30000) // 30 segundos
            listTaskViewModel.fetchTasksForUser()
        }
    }

    // Animaciones
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    val headerScale by animateFloatAsState(
        targetValue = if (tasks.isNotEmpty()) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "headerScale"
    )

    // Colores modernos (consistentes con CreateTask)
    val primaryColor = Color(0xFF6366F1) // Indigo moderno
    val secondaryColor = Color(0xFF8B5CF6) // Violeta
    val accentColor = Color(0xFF06B6D4) // Cyan
    val errorColor = Color(0xFFEF4444)
    val successColor = Color(0xFF10B981)

    // Dialog de confirmación para eliminar
    if (showDeleteDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    "Confirmar eliminación",
                    fontWeight = FontWeight.Bold,
                    color = errorColor
                )
            },
            text = {
                Text("¿Estás seguro de que deseas eliminar la tarea \"${taskToDelete?.title}\"?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        taskToDelete?.let { task ->
                            deleteTaskViewModel.deleteTask(task.id)
                        }
                        showDeleteDialog = false
                        taskToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = errorColor
                    )
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showDeleteDialog = false
                        taskToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.9f),
                        secondaryColor.copy(alpha = 0.7f),
                        accentColor.copy(alpha = 0.5f)
                    ),
                    start = androidx.compose.ui.geometry.Offset(
                        animatedOffset * 2,
                        animatedOffset * 1.5f
                    ),
                    end = androidx.compose.ui.geometry.Offset(
                        animatedOffset * 3,
                        animatedOffset * 2.5f
                    )
                )
            )
    ) {
        // Elementos decorativos de fondo
        repeat(4) { index ->
            val animatedY by infiniteTransition.animateFloat(
                initialValue = (-100).dp.value,
                targetValue = 1200.dp.value,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (10000 + index * 2500),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = "floatingElement$index"
            )

            Box(
                modifier = Modifier
                    .offset(
                        x = (30 + index * 100).dp,
                        y = animatedY.dp
                    )
                    .size((15 + index * 8).dp)
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        CircleShape
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Header animado
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(800, delayMillis = 100)
                ) + fadeIn(animationSpec = tween(800, delayMillis = 100))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.graphicsLayer {
                        scaleX = headerScale
                        scaleY = headerScale
                    }
                ) {
                    // Ícono de tareas
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .shadow(
                                elevation = 16.dp,
                                shape = CircleShape,
                                ambientColor = accentColor.copy(alpha = 0.3f)
                            )
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(accentColor, primaryColor)
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TaskAlt,
                            contentDescription = "Tasks",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Mis Tareas",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Gestiona tus actividades diarias • Auto-actualización activa",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }

            // Botón para crear nueva tarea
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(600, delayMillis = 200)
                ) + fadeIn(animationSpec = tween(600, delayMillis = 200))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = primaryColor.copy(alpha = 0.2f)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Crear Nueva Tarea",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "¡Agita el dispositivo para crear rápido!",
                                fontSize = 14.sp,
                                color = Color.Black.copy(alpha = 0.6f),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Button(
                            onClick = onNavigateToCreate,
                            modifier = Modifier
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    ambientColor = primaryColor.copy(alpha = 0.3f)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(primaryColor, secondaryColor)
                                        )
                                    )
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Agregar",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Agregar",
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Contenido principal
            when {
                isLoading -> {
                    // Estado de carga
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = primaryColor,
                                    strokeWidth = 3.dp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Actualizando tareas...",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = primaryColor
                                )
                            }
                        }
                    }
                }

                errorMessage != null -> {
                    // Estado de error
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        errorColor.copy(alpha = 0.1f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = "Error",
                                    tint = errorColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Error de conexión",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = errorColor
                            )
                            Text(
                                text = "Reintentando automáticamente...",
                                fontSize = 14.sp,
                                color = Color.Black.copy(alpha = 0.6f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                tasks.isEmpty() -> {
                    // Estado vacío
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                accentColor.copy(alpha = 0.2f),
                                                accentColor.copy(alpha = 0.1f)
                                            )
                                        ),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ListAlt,
                                    contentDescription = "Empty",
                                    tint = accentColor,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "¡Es hora de ser productivo!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "No tienes tareas pendientes. Crea tu primera tarea para comenzar a organizar tu día.",
                                fontSize = 14.sp,
                                color = Color.Black.copy(alpha = 0.6f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp),
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = onNavigateToCreate,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(primaryColor, secondaryColor)
                                            )
                                        )
                                        .padding(horizontal = 24.dp, vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Create",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Crear Primera Tarea",
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {
                    // Lista de tareas
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(tasks) { task ->
                            AnimatedVisibility(
                                visible = true,
                                enter = slideInVertically(
                                    initialOffsetY = { it },
                                    animationSpec = tween(400)
                                ) + fadeIn(animationSpec = tween(400))
                            ) {
                                ModernTaskCard(
                                    task = task,
                                    onEdit = { onNavigateToUpdate(task.id) },
                                    onDelete = {
                                        taskToDelete = task
                                        showDeleteDialog = true
                                    },
                                    primaryColor = primaryColor,
                                    secondaryColor = secondaryColor,
                                    errorColor = errorColor
                                )
                            }
                        }
                    }
                }
            }

            // Mensaje de estado de eliminación
            AnimatedVisibility(
                visible = deleteStatusMessage.isNotEmpty(),
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (deleteStatusMessage == "Task deleted successfully") {
                            successColor.copy(alpha = 0.1f)
                        } else {
                            errorColor.copy(alpha = 0.1f)
                        }
                    )
                ) {
                    Text(
                        text = if (deleteStatusMessage == "Task deleted successfully") {
                            "¡Tarea eliminada exitosamente!"
                        } else {
                            deleteStatusMessage
                        },
                        modifier = Modifier.padding(16.dp),
                        color = if (deleteStatusMessage == "Task deleted successfully") {
                            successColor
                        } else {
                            errorColor
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ModernTaskCard(
    task: Task,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    primaryColor: Color,
    secondaryColor: Color,
    errorColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = primaryColor.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header de la tarea con ícono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.2f),
                                    primaryColor.copy(alpha = 0.1f)
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = "Task",
                        tint = primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = task.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            // Descripción de la tarea
            Text(
                text = task.description,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 20.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            // Botones de acción con estilo moderno
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Botón de editar
                OutlinedButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor,
                        containerColor = primaryColor.copy(alpha = 0.05f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        primaryColor.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Editar",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Botón de eliminar
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = errorColor,
                        containerColor = errorColor.copy(alpha = 0.05f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        errorColor.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Eliminar",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}