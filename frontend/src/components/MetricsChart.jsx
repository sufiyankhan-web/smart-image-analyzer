import { RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, Radar, ResponsiveContainer, Tooltip } from 'recharts';

function CustomTooltip({ active, payload }) {
  if (!active || !payload?.length) return null;
  return (
    <div className="rounded-lg border border-white/10 bg-brand-panel px-3 py-2 text-xs shadow-xl">
      <p className="text-brand-muted">{payload[0]?.payload?.subject}</p>
      <p className="font-semibold text-brand-accent">{Math.round(payload[0]?.value)}/100</p>
    </div>
  );
}

function MetricsChart({ data }) {
  const chartData = [
    { subject: 'Sharpness', value: Math.round(data.sharpnessScore || Math.min(100, (data.blurVariance || 0) / 12)) },
    { subject: 'Exposure', value: Math.round(data.exposureScore || (data.brightness > 128 ? 100 - (data.brightness - 128) : (data.brightness / 128) * 100)) },
    { subject: 'Composition', value: Math.round(data.compositionScore || 0) },
    { subject: 'Symmetry', value: Math.round(data.symmetryScore || Math.min(100, ((data.contrast || 0) / 85) * 100)) },
    { subject: 'Noise', value: Math.round(data.noiseScore || Math.max(0, 100 - (data.overexposurePercent || 0))) }
  ];

  return (
    <div className="glass-card p-6">
      <div className="mb-1">
        <h3 className="text-sm font-semibold text-white">Quality Metrics Radar</h3>
        <p className="mt-0.5 text-[11px] text-brand-muted">5-axis overview of all analysis modules</p>
      </div>
      <div className="mt-3 h-56 w-full">
        <ResponsiveContainer width="100%" height="100%">
          <RadarChart data={chartData}>
            <PolarGrid stroke="rgba(255,255,255,0.06)" />
            <PolarAngleAxis
              dataKey="subject"
              tick={{ fill: '#94a3b8', fontSize: 11 }}
            />
            <PolarRadiusAxis domain={[0, 100]} tick={false} axisLine={false} />
            <Tooltip content={<CustomTooltip />} />
            <Radar
              dataKey="value"
              stroke="#22d3ee"
              strokeWidth={1.5}
              fill="#818cf8"
              fillOpacity={0.3}
            />
          </RadarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}

export default MetricsChart;
