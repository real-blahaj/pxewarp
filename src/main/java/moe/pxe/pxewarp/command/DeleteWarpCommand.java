package moe.pxe.pxewarp.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import moe.pxe.pxewarp.Main;
import moe.pxe.pxewarp.Warp;
import moe.pxe.pxewarp.Warps;
import moe.pxe.pxewarp.command.argument.WarpArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class DeleteWarpCommand {

    public static LiteralCommandNode<CommandSourceStack> getCommand() {
        Runnable saveConfig = Main.getInstance()::saveWarpConfig;
        return Commands.literal("delwarp")
                .requires(ctx -> ctx.getSender().hasPermission("warps.delete") || ctx.getSender().isOp())
                .then(Commands.argument("warp", new WarpArgument())
                        .executes(ctx -> {
                            Warp warp = ctx.getArgument("warp", Warp.class);

                            Warps.deleteWarp(warp.getName());
                            saveConfig.run();
                            ctx.getSource().getSender().sendRichMessage("Deleted warp <name>", Placeholder.unparsed("name", warp.getName()));
                            ctx.getSource().getSender().playSound(Main.DELETE_SOUND);
                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
    }

}
