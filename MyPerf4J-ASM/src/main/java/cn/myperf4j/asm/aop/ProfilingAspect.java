package cn.myperf4j.asm.aop;

import cn.myperf4j.asm.ASMRecorderMaintainer;
import cn.myperf4j.base.config.ProfilingParams;
import cn.myperf4j.bridge.Bridge;
import cn.myperf4j.core.MethodTagMaintainer;
import cn.myperf4j.core.recorder.Recorder;
import cn.myperf4j.base.util.Logger;

import java.lang.reflect.Method;

/**
 * Created by LinShunkang on 2018/4/15
 */
public final class ProfilingAspect {

    private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();

    private static volatile ASMRecorderMaintainer recorderMaintainer;

    private static volatile boolean running = false;

    public static void profiling(final long startNanos, final int methodTagId) {
        try {
            if (true == Bridge.isStopRecord()) {
                return;
            }

            if (!running) {
                Logger.warn("ProfilingAspect.profiling(): methodTagId=" + methodTagId + ", methodTag=" + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ", startNanos: " + startNanos + ", IGNORED!!!");
                return;
            }

            Recorder recorder = recorderMaintainer.getRecorder(methodTagId);
            if (recorder == null) {
                Logger.warn("ProfilingAspect.profiling(): methodTagId=" + methodTagId + ", methodTag=" + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ", startNanos: " + startNanos + ", recorder is null IGNORED!!!");
                return;
            }

            recorder.recordTime(startNanos, System.nanoTime());
        } catch (Exception e) {
            Logger.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", " + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }

    //InvocationHandler.invoke(Object proxy, Method method, Object[] args)
    public static void profiling(final long startNanos, final Method method) {
        try {

            if (true == Bridge.isStopRecord()) {
                return;
            }

            if (!running) {
                Logger.warn("ProfilingAspect.profiling(): method=" + method + ", startNanos: " + startNanos + ", IGNORED!!!");
                return;
            }

            int methodTagId = methodTagMaintainer.addMethodTag(method);
            if (methodTagId < 0) {
                Logger.warn("ProfilingAspect.profiling(): method=" + method + ", startNanos: " + startNanos + ", methodTagId < 0!!!");
                return;
            }

            Recorder recorder = recorderMaintainer.getRecorder(methodTagId);
            if (recorder == null) {
                synchronized (ProfilingAspect.class) {
                    recorder = recorderMaintainer.getRecorder(methodTagId);
                    if (recorder == null) {
                        recorderMaintainer.addRecorder(methodTagId, ProfilingParams.of(3000, 10));
                        recorder = recorderMaintainer.getRecorder(methodTagId);
                    }
                }
            }

            recorder.recordTime(startNanos, System.nanoTime());
        } catch (Exception e) {
            Logger.error("ProfilingAspect.profiling(" + startNanos + ", " + method + ")", e);
        }
    }

    public static void setRecorderMaintainer(ASMRecorderMaintainer maintainer) {
        recorderMaintainer = maintainer;
    }

    public static void setRunning(boolean run) {
        running = run;
    }
}
