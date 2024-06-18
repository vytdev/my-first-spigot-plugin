package com.vytdev.testPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;

public class RemoveWitherSkeletonsEvent implements Listener {

	/**
	 * @constructor
	 */
	private final JavaPlugin plugin;
	public RemoveWitherSkeletonsEvent(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * listens for entity spawns
	 */
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		// get spark instance
		Spark spark = SparkProvider.get();

		// tps statistics
		DoubleStatistic<StatisticWindow.TicksPerSecond> tps = spark.tps();

		// i dont know how to get the current tps right after this entity is
		// spawned, but i hope this may do :)
		double tpsVal = tps.poll(StatisticWindow.TicksPerSecond.SECONDS_5);

		if (tpsVal < 20) {
			// remove nearby wither skeletons close to this spawned entity
			// on 10 blocks radius
			removeNearbyWitherSkeletons(event.getEntity(), 10);
		}
	}

	/**
	 * despawn nearby wither skeletons
	 * @private
	 */
	private void removeNearbyWitherSkeletons(Entity entity, double radius) {
		for (Entity nearbyEntity : entity.getNearbyEntities(radius, radius, radius)) {
			// must be a wither skeleton
			if (nearbyEntity.getType() == EntityType.WITHER_SKELETON) {
				WitherSkeleton witherSkeleton = (WitherSkeleton) nearbyEntity;
				witherSkeleton.remove();
			}
		}
	}
}
