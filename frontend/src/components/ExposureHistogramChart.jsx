import { ResponsiveContainer, AreaChart, Area, XAxis, YAxis, Tooltip, ReferenceLine } from 'recharts';

function CustomTooltip({ active, payload, label }) {
  if (!active || !payload?.length) return null;
  return (
    <div className="rounded-lg border border-white/10 bg-brand-panel px-3 py-2 text-xs shadow-xl">
      <p className="text-brand-muted">Intensity: <span className="font-mono text-brand-text">{label}</span></p>
      <p className="text-brand-muted">Count: <span className="font-mono text-brand-accent">{payload[0]?.value?.toLocaleString()}</span></p>
    </div>
  );
}

function ExposureHistogramChart({ histogram = [] }) {
  const chartData = histogram.map((count, idx) => ({ intensity: idx, count }));

  return (
    <div className="glass-card p-6">
      <div className="mb-1">
        <h3 className="text-sm font-semibold text-white">Exposure Histogram</h3>
        <p className="mt-0.5 text-[11px] text-brand-muted">
          Left = shadows &nbsp;&bull;&nbsp; Right = highlights &nbsp;&bull;&nbsp; Clipping at extremes
        </p>
      </div>

      <div className="mt-5 h-52 w-full">
        <ResponsiveContainer width="100%" height="100%">
          <AreaChart data={chartData} margin={{ top: 4, right: 4, bottom: 0, left: -20 }}>
            <defs>
              <linearGradient id="histGrad" x1="0" y1="0" x2="1" y2="0">
                <stop offset="0%" stopColor="#818cf8" stopOpacity={0.7} />
                <stop offset="50%" stopColor="#22d3ee" stopOpacity={0.7} />
                <stop offset="100%" stopColor="#fbbf24" stopOpacity={0.7} />
              </linearGradient>
              <linearGradient id="histFill" x1="0" y1="0" x2="1" y2="0">
                <stop offset="0%" stopColor="#818cf8" stopOpacity={0.25} />
                <stop offset="50%" stopColor="#22d3ee" stopOpacity={0.2} />
                <stop offset="100%" stopColor="#fbbf24" stopOpacity={0.25} />
              </linearGradient>
            </defs>
            <XAxis
              dataKey="intensity"
              tick={{ fill: '#64748b', fontSize: 10 }}
              tickCount={9}
              axisLine={{ stroke: 'rgba(255,255,255,0.05)' }}
              tickLine={false}
            />
            <YAxis hide />
            <Tooltip content={<CustomTooltip />} />
            <ReferenceLine x={38} stroke="rgba(129,140,248,0.3)" strokeDasharray="3 3" />
            <ReferenceLine x={217} stroke="rgba(251,191,36,0.3)" strokeDasharray="3 3" />
            <Area
              type="monotone"
              dataKey="count"
              stroke="url(#histGrad)"
              strokeWidth={1.5}
              fill="url(#histFill)"
            />
          </AreaChart>
        </ResponsiveContainer>
      </div>

      <div className="mt-3 flex justify-between text-[10px] text-brand-muted">
        <span className="text-brand-primary">Shadows</span>
        <span className="text-brand-accent">Midtones</span>
        <span className="text-amber-400">Highlights</span>
      </div>
    </div>
  );
}

export default ExposureHistogramChart;
