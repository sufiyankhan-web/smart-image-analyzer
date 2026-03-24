import { ResponsiveContainer, BarChart, XAxis, YAxis, Tooltip, Bar } from 'recharts';

function BatchAnalysisCard({ batchResult }) {
  if (!batchResult) return null;

  return (
    <div className="glass-card p-6">
      <h3 className="text-lg font-semibold text-white">Batch Image Analysis</h3>
      <div className="mt-4 grid gap-3 sm:grid-cols-3">
        <div className="rounded-xl bg-brand-panelSoft/70 p-3 text-sm">
          <p className="text-brand-muted">Average Score</p>
          <p className="mt-1 text-xl font-semibold text-white">{Math.round(batchResult.averageQualityScore || 0)}</p>
        </div>
        <div className="rounded-xl bg-brand-panelSoft/70 p-3 text-sm">
          <p className="text-brand-muted">Best Image</p>
          <p className="mt-1 font-medium text-brand-text">{batchResult.bestImage}</p>
        </div>
        <div className="rounded-xl bg-brand-panelSoft/70 p-3 text-sm">
          <p className="text-brand-muted">Worst Image</p>
          <p className="mt-1 font-medium text-brand-text">{batchResult.worstImage}</p>
        </div>
      </div>

      <div className="mt-5 h-64 w-full">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={batchResult.qualityDistribution || []}>
            <XAxis dataKey="range" tick={{ fill: '#94a3b8' }} />
            <YAxis allowDecimals={false} tick={{ fill: '#94a3b8' }} />
            <Tooltip
              contentStyle={{ backgroundColor: '#111827', border: '1px solid rgba(148,163,184,0.25)' }}
              labelStyle={{ color: '#d1d5db' }}
            />
            <Bar dataKey="count" fill="#38bdf8" radius={[8, 8, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}

export default BatchAnalysisCard;
