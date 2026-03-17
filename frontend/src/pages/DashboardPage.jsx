import { useEffect, useState } from "react";
import { useLocation, useParams, Link } from "react-router-dom";
import { ArrowLeft, Award, Layers, Sparkles, Zap, Image as ImageIcon, Film, Sun, TrendingUp, Moon, Monitor, Smile } from "lucide-react";
import Navbar from "../components/Navbar";
import LoadingSpinner from "../components/LoadingSpinner";
import ScoreRing from "../components/ScoreRing";
import SuggestionsPanel from "../components/SuggestionsPanel";
import MetricsChart from "../components/MetricsChart";
import ExposureHistogramChart from "../components/ExposureHistogramChart";
import CompositionOverlayCard from "../components/CompositionOverlayCard";
import ExifMetadataCard from "../components/ExifMetadataCard";
import ModuleScoreCards from "../components/ModuleScoreCards";
import { getImageAnalysisById } from "../services/api";
import { scoreToLabel } from "../utils/analysisHelpers";

function scoreColor(score) {
  if (score >= 85) return "#10b981";
  if (score >= 70) return "#06b6d4";
  if (score >= 55) return "#8b5cf6";
  if (score >= 40) return "#f59e0b";
  return "#ef4444";
}

const SUMMARY_TILES = (analysis) => [
  {
    label: "Scene",
    value: analysis?.sceneType
      ? analysis.sceneType.charAt(0).toUpperCase() + analysis.sceneType.slice(1)
      : "General",
    Icon: Film
  },
  { label: "Exposure", value: analysis?.exposureBalance || "Balanced", Icon: Sun },
  { label: "Highlights", value: `${Math.round(analysis?.highlightClippingPercent || 0)}%`, Icon: TrendingUp },
  { label: "Shadows", value: `${Math.round(analysis?.shadowClippingPercent || 0)}%`, Icon: Moon },
  {
    label: "Resolution",
    value: analysis?.imageWidth && analysis?.imageHeight ? `${analysis.imageWidth}x${analysis.imageHeight}` : "N/A",
    Icon: Monitor
  },
  { label: "Face", value: analysis?.faceDetected ? "Detected" : "None", Icon: Smile },
];

function DashboardPage() {
  const { id } = useParams();
  const location = useLocation();
  const [analysis, setAnalysis] = useState(location.state?.analysis || null);
  const [previewUrl] = useState(location.state?.previewUrl || "");
  const [loading, setLoading] = useState(Boolean(id) && !analysis);
  const [error, setError] = useState("");
  const [activeTab, setActiveTab] = useState("overview");

  useEffect(() => {
    if (!id || analysis) return;
    (async () => {
      try {
        const data = await getImageAnalysisById(id);
        setAnalysis(data);
      } catch (apiError) {
        setError(apiError.response?.data?.message || "Could not load analysis.");
      } finally {
        setLoading(false);
      }
    })();
  }, [analysis, id]);

  if (loading) {
    return (
      <div>
        <Navbar />
        <main className="mx-auto max-w-7xl px-6 py-20 text-center">
          <LoadingSpinner label="Analyzing your image..." />
        </main>
      </div>
    );
  }

  const grade = scoreToLabel(analysis?.finalScore);
  const gradeHex = scoreColor(analysis?.finalScore || 0);

  return (
    <div className="relative min-h-screen overflow-hidden">
      <Navbar />

      {/* Ultra-premium background with gradient mesh */}
      <div className="fixed inset-0 -z-10">
        <div className="absolute inset-0 bg-gradient-to-br from-slate-950 via-purple-950 to-slate-950" />
        <div className="absolute top-0 -left-40 w-80 h-80 bg-purple-600/10 rounded-full blur-[100px]" />
        <div className="absolute top-1/3 -right-40 w-96 h-96 bg-cyan-500/5 rounded-full blur-[120px]" />
        <div className="absolute bottom-0 left-1/4 w-80 h-80 bg-blue-600/5 rounded-full blur-[100px]" />
      </div>

      <main className="relative z-10 mx-auto max-w-7xl px-6 py-12">
        {/* -- Brand Header -- */}
        {analysis && (
          <div className="mb-10 flex items-center gap-3 group cursor-default">
            <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-gradient-to-br from-purple-500 to-cyan-400 shadow-lg group-hover:shadow-xl transition-all">
              <ImageIcon className="h-6 w-6 text-white" />
            </div>
            <div>
              <h1 className="text-sm font-black uppercase tracking-widest text-transparent bg-clip-text bg-gradient-to-r from-purple-300 via-cyan-300 to-purple-300">
                Smart Image Analyzer
              </h1>
              <p className="text-xs text-slate-400 mt-0.5">Professional Quality Intelligence</p>
            </div>
          </div>
        )}

        {error && (
          <div className="mb-6 rounded-xl border border-red-500/20 bg-red-500/5 p-4 text-sm text-red-300 backdrop-blur">
            {error}
          </div>
        )}

        {!analysis && !loading && !error && (
          <div className="flex flex-col items-center justify-center py-24 text-center">
            <div className="mb-6 flex h-20 w-20 items-center justify-center rounded-2xl bg-gradient-to-br from-purple-500/10 to-cyan-500/10 border border-purple-500/20">
              <Layers className="h-10 w-10 text-slate-400" />
            </div>
            <p className="text-slate-400 max-w-sm">No analysis loaded. Upload an image to begin your quality journey.</p>
            <Link
              to="/"
              className="mt-6 inline-flex items-center gap-2 rounded-xl bg-gradient-to-r from-purple-600 to-cyan-500 px-6 py-3 text-sm font-bold text-white transition-all hover:shadow-lg hover:shadow-purple-500/50 active:scale-95"
            >
              <Sparkles className="h-4 w-4" />
              Upload Image
            </Link>
          </div>
        )}

        {analysis && (
          <>
            {/* -- Hero Score Section -- */}
            <div className="mb-8 grid gap-6 lg:grid-cols-2 animate-fade-in">
              {/* Score Card */}
              <div className="group relative rounded-2xl border border-purple-500/10 bg-gradient-to-br from-purple-900/20 to-purple-900/5 p-8 backdrop-blur-md transition-all hover:border-purple-500/20 hover:bg-purple-900/10">
                <div className="absolute -top-3 -right-3 flex h-12 w-12 items-center justify-center rounded-xl bg-gradient-to-br from-purple-500 to-cyan-400 shadow-lg">
                  <Award className="h-6 w-6 text-white" />
                </div>
                <div className="mb-6 space-y-1">
                  <p className="text-xs font-semibold uppercase tracking-widest text-slate-400">Overall Quality Score</p>
                  <h2 className="text-5xl font-black text-white">{Math.round(analysis.finalScore)}</h2>
                </div>
                <div className="space-y-3">
                  <div className="flex items-center gap-3">
                    <div
                      className="h-3 w-full rounded-full bg-slate-700/50"
                      style={{ overflow: "hidden" }}
                    >
                      <div
                        className="h-full rounded-full transition-all duration-500"
                        style={{
                          width: `${analysis.finalScore}%`,
                          background: `linear-gradient(90deg, ${gradeHex}, ${gradeHex}dd)`
                        }}
                      />
                    </div>
                  </div>
                  <p
                    className="text-sm font-bold"
                    style={{ color: gradeHex }}
                  >
                    {grade} Rating • {analysis.fileName}
                  </p>
                </div>
              </div>

              {/* Quick Stats */}
              <div className="rounded-2xl border border-cyan-500/10 bg-gradient-to-br from-cyan-900/20 to-cyan-900/5 p-8 backdrop-blur-md">
                <div className="mb-4 flex items-center gap-2">
                  <Zap className="h-5 w-5 text-cyan-400" />
                  <h3 className="text-sm font-bold uppercase tracking-wide text-slate-200">Key Metrics</h3>
                </div>
                <div className="grid grid-cols-2 gap-3">
                  {SUMMARY_TILES(analysis).slice(0, 4).map(({ label, value, Icon }) => (
                    <div key={label} className="rounded-lg bg-slate-900/30 p-3 border border-slate-700/30 transition-all hover:border-cyan-500/30 hover:bg-slate-900/50">
                      <Icon className="h-5 w-5 mb-2 text-cyan-400" />
                      <p className="text-[11px] font-semibold uppercase text-slate-400">{label}</p>
                      <p className="text-sm font-bold text-slate-100 mt-1">{value}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* -- Tabs Navigation -- */}
            <div className="mb-8 flex gap-2 border-b border-slate-800">
              {["Overview", "Detailed", "Insights"].map((tab) => (
                <button
                  key={tab}
                  onClick={() => setActiveTab(tab.toLowerCase())}
                  className={`px-6 py-3 text-sm font-semibold transition-all relative ${
                    activeTab === tab.toLowerCase()
                      ? "text-transparent bg-clip-text bg-gradient-to-r from-purple-300 to-cyan-300"
                      : "text-slate-400 hover:text-slate-300"
                  }`}
                >
                  {tab}
                  {activeTab === tab.toLowerCase() && (
                    <div className="absolute bottom-0 left-0 right-0 h-1 bg-gradient-to-r from-purple-500 to-cyan-400 rounded-t" />
                  )}
                </button>
              ))}
            </div>

            {/* -- Overview Tab -- */}
            {activeTab === "overview" && (
              <div className="space-y-6 animate-fade-in">
                {/* Ring + Summary */}
                <div className="grid gap-6 lg:grid-cols-2">
                  <div className="rounded-2xl border border-purple-500/10 bg-gradient-to-br from-purple-900/20 to-purple-900/5 p-8 backdrop-blur-md flex items-center justify-center hover:border-purple-500/20 transition-all">
                    <ScoreRing score={analysis.finalScore} />
                  </div>

                  <div className="rounded-2xl border border-cyan-500/10 bg-gradient-to-br from-cyan-900/20 to-cyan-900/5 p-8 backdrop-blur-md space-y-4">
                    <h4 className="font-bold text-xl text-white">Analysis Overview</h4>
                    {SUMMARY_TILES(analysis).map(({ label, value, Icon }) => (
                      <div key={label} className="flex items-center justify-between rounded-lg bg-slate-900/30 px-4 py-3 border border-slate-700/20 hover:border-slate-600/40 transition-all">
                        <span className="flex items-center gap-2 text-slate-300">
                          <Icon className="h-4 w-4 text-cyan-400" />
                          <span className="text-sm font-medium">{label}</span>
                        </span>
                        <span className="font-bold text-transparent bg-clip-text bg-gradient-to-r from-purple-300 to-cyan-300">{value}</span>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Module Cards */}
                <div className="rounded-2xl border border-slate-700/50 bg-gradient-to-br from-slate-900/30 to-slate-900/10 p-8 backdrop-blur-md">
                  <h3 className="mb-6 font-bold text-xl text-white">Analysis Modules</h3>
                  <ModuleScoreCards analysis={analysis} />
                </div>

                {/* Composition Overlay */}
                <div className="rounded-2xl border border-slate-700/50 bg-slate-900/20 p-6 backdrop-blur-md overflow-hidden hover:border-slate-600/50 transition-all">
                  <h3 className="mb-4 font-bold text-white flex items-center gap-2">
                    <Sparkles className="h-4 w-4 text-cyan-400" />
                    Visual Analysis
                  </h3>
                  <CompositionOverlayCard src={previewUrl} analysis={analysis} />
                </div>
              </div>
            )}

            {/* -- Detailed Tab -- */}
            {activeTab === "detailed" && (
              <div className="space-y-6 animate-fade-in">
                <div className="grid gap-6 lg:grid-cols-2">
                  <div className="rounded-2xl border border-slate-700/50 bg-gradient-to-br from-slate-900/30 to-slate-900/10 p-8 backdrop-blur-md">
                    <SuggestionsPanel suggestions={analysis.suggestions || []} />
                  </div>
                  <div className="rounded-2xl border border-slate-700/50 bg-gradient-to-br from-slate-900/30 to-slate-900/10 p-8 backdrop-blur-md">
                    <ExposureHistogramChart histogram={analysis.histogram || []} />
                  </div>
                </div>
              </div>
            )}

            {/* -- Insights Tab -- */}
            {activeTab === "insights" && (
              <div className="space-y-6 animate-fade-in">
                <div className="grid gap-6 lg:grid-cols-2">
                  <div className="rounded-2xl border border-slate-700/50 bg-gradient-to-br from-slate-900/30 to-slate-900/10 p-8 backdrop-blur-md">
                    <MetricsChart data={analysis} />
                  </div>
                  <div className="rounded-2xl border border-slate-700/50 bg-gradient-to-br from-slate-900/30 to-slate-900/10 p-8 backdrop-blur-md">
                    <ExifMetadataCard analysis={analysis} />
                  </div>
                </div>
              </div>
            )}

            {/* -- Footer CTA -- */}
            <div className="mt-12 rounded-2xl border border-gradient-to-r border-purple-500/20 bg-gradient-to-r from-purple-900/20 to-cyan-900/20 p-8 text-center backdrop-blur-md">
              <div className="space-y-4">
                <h3 className="text-2xl font-black text-white">
                  Analyze Another Image
                </h3>
                <p className="text-slate-400">
                  Get instant quality insights powered by AI-driven Smart Image Analyzer
                </p>
                <Link
                  to="/"
                  className="inline-flex items-center gap-2 rounded-xl bg-gradient-to-r from-purple-600 to-cyan-500 px-8 py-4 text-sm font-bold text-white transition-all hover:shadow-lg hover:shadow-purple-500/50 active:scale-95"
                >
                  <Sparkles className="h-5 w-5" />
                  New Analysis
                </Link>
              </div>
            </div>
          </>
        )}
      </main>
    </div>
  );
}

export default DashboardPage;
