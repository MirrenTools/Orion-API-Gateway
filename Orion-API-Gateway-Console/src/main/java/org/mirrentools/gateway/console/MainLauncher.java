package org.mirrentools.gateway.console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

/**
 * 启动器
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class MainLauncher extends Launcher {

	/**
	 * 启动软件要启动的类
	 * 
	 * @param clz
	 */
	public static void start(Class<?> clz) {
		main(new String[] { "run", clz.getName() });
	}

	/**
	 * Main entry point.
	 *
	 * @param args
	 *          the user command line arguments.
	 */
	public static void main(String[] args) {
		InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
		System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");
		System.setProperty("vertx.disableDnsResolver", "true");
		new MainLauncher().dispatch(args);
	}

	/**
	 * Utility method to execute a specific command.
	 *
	 * @param cmd
	 *          the command
	 * @param args
	 *          the arguments
	 */
	public static void executeCommand(String cmd, String... args) {
		new MainLauncher().execute(cmd, args);
	}

	/**
	 * Hook for sub-classes of {@link Launcher} before the vertx instance is
	 * started.
	 *
	 * @param options
	 *          the configured Vert.x options. Modify them to customize the Vert.x
	 *          instance.
	 */
	public void beforeStartingVertx(VertxOptions options) {
		options.setBlockedThreadCheckInterval(1000 * 60);
		options.setPreferNativeTransport(true);
	}

	/**
	 * Hook for sub-classes of {@link Launcher} before the verticle is deployed.
	 *
	 * @param deploymentOptions
	 *          the current deployment options. Modify them to customize the
	 *          deployment.
	 */
	public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
		String root = System.getProperty("user.dir");
		Path path = Paths.get(root, "config", "config.json");
		byte[] bs;
		try {
			bs = Files.readAllBytes(path);
			JsonObject config = new JsonObject(new String(bs));
			// 加载配置
			deploymentOptions.setConfig(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
