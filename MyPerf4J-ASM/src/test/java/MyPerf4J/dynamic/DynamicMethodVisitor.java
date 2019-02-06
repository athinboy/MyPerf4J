package MyPerf4J.dynamic;

import cn.myperf4j.asm.aop.ProfilingAspect;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by LinShunkang on 2018/4/15
 */
public class DynamicMethodVisitor extends AdviceAdapter {

    private static final String PROFILING_ASPECT_INNER_NAME = Type.getInternalName(ProfilingAspect.class);

    private int startTimeIdentifier;

    private String name;

    private String desc;

    public DynamicMethodVisitor(int access,
                                String name,
                                String desc,
                                MethodVisitor mv) {
        super(ASM5, mv, access, name, desc);
        this.name = name;
        this.desc = desc;
    }

    @Override
    protected void onMethodEnter() {
        if (profiling()) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            startTimeIdentifier = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, startTimeIdentifier);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (profiling() && ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW)) {
            mv.visitVarInsn(LLOAD, startTimeIdentifier);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitMethodInsn(INVOKESTATIC, PROFILING_ASPECT_INNER_NAME, "profiling", "(JLjava/lang/reflect/Method;)V", false);
        }
    }

    private boolean profiling() {
        return true;
    }
}
