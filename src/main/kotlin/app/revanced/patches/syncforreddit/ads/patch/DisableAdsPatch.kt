package app.revanced.patches.syncforreddit.ads.patch

import app.revanced.extensions.toErrorResult
import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.syncforreddit.ads.annotations.DisableAdsCompatibility
import app.revanced.patches.syncforreddit.ads.fingerprints.IsAdsEnabledFingerprint
import app.revanced.patches.syncforreddit.detection.piracy.patch.DisablePiracyDetectionPatch

@Patch
@Name("disable-ads")
@DependsOn([DisablePiracyDetectionPatch::class])
@Description("Disables ads.")
@Version("0.0.1")
@DisableAdsCompatibility
class DisableAdsPatch : BytecodePatch(listOf(IsAdsEnabledFingerprint)) {
    override fun execute(context: BytecodeContext): PatchResult {
        IsAdsEnabledFingerprint.result?.mutableMethod?.apply {
            addInstructions(
                0,
                """
                const/4 v0, 0x0
                return v0
            """
            )
        } ?: return IsAdsEnabledFingerprint.toErrorResult()

        return PatchResultSuccess()
    }

}